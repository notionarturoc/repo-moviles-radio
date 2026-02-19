package com.tomorrowland.audioplayer.ui.nowplaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.tomorrowland.audioplayer.R
import com.tomorrowland.audioplayer.data.Track
import com.tomorrowland.audioplayer.data.TrackRepository
import com.tomorrowland.audioplayer.databinding.FragmentNowPlayingBinding
import com.tomorrowland.audioplayer.utils.AudioPlayerManager

/**
 * ============================================================================
 * Tomorrowland Audio Player - Now Playing Fragment (Reproductor Completo)
 * ============================================================================
 *
 * 🎧 NOW PLAYING FRAGMENT:
 *
 * Esta es la pantalla de reproducción completa. Muestra:
 * 1. Album art grande con efecto glow
 * 2. Información del track (título, artista)
 * 3. Controles de reproducción completos
 * 4. Barra de progreso interactiva
 *
 * 📝 CONCEPTOS CLAVE:
 *
 * 1. SEEKBAR:
 *    - Permite al usuario saltar a cualquier posición
 *    - OnSeekBarChangeListener para detectar interacción
 *
 * 2. ARGUMENTOS DE NAVEGACIÓN:
 *    - Recibe el ID del track desde el HomeFragment
 *    - Usa Safe Args generado por Navigation Component
 *
 * 3. OBSERVER PATTERN:
 *    - Escucha eventos del AudioPlayerManager
 *    - Actualiza la UI en tiempo real
 */
class NowPlayingFragment : Fragment(), AudioPlayerManager.AudioPlayerListener {

    // === View Binding ===
    private var _binding: FragmentNowPlayingBinding? = null
    private val binding get() = _binding!!

    // === Track actual ===
    private var currentTrack: Track? = null

    // === Control de SeekBar ===
    // Flag para evitar conflictos cuando el usuario arrastra el SeekBar
    private var isUserSeeking = false

    // === Estados de los botones ===
    private var isShuffleEnabled = false
    private var isRepeatEnabled = false
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el track actual del AudioPlayerManager
        currentTrack = AudioPlayerManager.currentTrack

        // Configurar la UI
        setupUI()

        // Configurar los controles
        setupControls()

        // Configurar el SeekBar
        setupSeekBar()

        // Registrar como listener
        AudioPlayerManager.setListener(this)

        // Mostrar el track actual
        currentTrack?.let { updateUI(it) }

        // Actualizar estado de play/pause
        updatePlayPauseButton(AudioPlayerManager.isPlaying)
    }

    /**
     * Configura los elementos de UI iniciales.
     */
    private fun setupUI() {
        // Botón de volver
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Botón de más opciones
        binding.btnMore.setOnClickListener {
            // TODO: Mostrar menú de opciones
        }
    }

    /**
     * Configura los controles de reproducción.
     */
    private fun setupControls() {
        // Botón Play/Pause
        binding.btnPlayPause.setOnClickListener {
            AudioPlayerManager.togglePlayPause()
        }

        // Botón Previous
        binding.btnPrevious.setOnClickListener {
            AudioPlayerManager.playPrevious(requireContext())
        }

        // Botón Next
        binding.btnNext.setOnClickListener {
            AudioPlayerManager.playNext(requireContext())
        }

        // Botón Shuffle
        binding.btnShuffle.setOnClickListener {
            isShuffleEnabled = !isShuffleEnabled
            updateShuffleButton()
        }

        // Botón Repeat
        binding.btnRepeat.setOnClickListener {
            isRepeatEnabled = !isRepeatEnabled
            updateRepeatButton()
        }

        // Botón Favorito
        binding.btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteButton()
        }

        // Botón Devices (AirPlay)
        binding.btnDevices.setOnClickListener {
            // TODO: Mostrar selector de dispositivos
        }

        // Botón Queue
        binding.btnQueue.setOnClickListener {
            // TODO: Mostrar cola de reproducción
        }
    }

    /**
     * Configura el SeekBar para controlar el progreso.
     *
     * 📝 SEEKBAR LISTENER:
     * - onProgressChanged: Se llama cuando cambia el progreso
     * - onStartTrackingTouch: El usuario empieza a arrastrar
     * - onStopTrackingTouch: El usuario suelta el SeekBar
     */
    private fun setupSeekBar() {
        binding.progressSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Solo actualizar el tiempo si el usuario está arrastrando
                if (fromUser && currentTrack != null) {
                    val duration = currentTrack!!.durationMs
                    val position = (progress.toLong() * duration) / 100
                    binding.currentTime.text = AudioPlayerManager.formatTime(position)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // El usuario empezó a arrastrar
                isUserSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // El usuario soltó el SeekBar
                isUserSeeking = false

                seekBar?.let {
                    currentTrack?.let { track ->
                        // Calcular la posición en milisegundos
                        val position = (it.progress.toLong() * track.durationMs) / 100
                        AudioPlayerManager.seekTo(position)
                    }
                }
            }
        })
    }

    /**
     * Actualiza toda la UI con la información del track.
     *
     * @param track El track a mostrar
     */
    private fun updateUI(track: Track) {
        binding.apply {
            // Título y artista
            trackTitle.text = track.title
            trackArtist.text = track.artist

            // Duración total
            totalTime.text = track.duration

            // Tiempo actual (empezar en 0:00)
            currentTime.text = "0:00"

            // Estado de favorito
            isFavorite = track.isFavorite
            updateFavoriteButton()

            // Cargar album art
            Glide.with(this@NowPlayingFragment)
                .load(track.albumArtUrl)
                .placeholder(R.drawable.bg_gradient_tomorrowland)
                .centerCrop()
                .into(albumArt)

            // Cargar efecto glow (la misma imagen con blur)
            Glide.with(this@NowPlayingFragment)
                .load(track.albumArtUrl)
                .placeholder(R.drawable.bg_gradient_tomorrowland)
                .centerCrop()
                .into(albumArtGlow)
        }
    }

    /**
     * Actualiza el botón de play/pause.
     */
    private fun updatePlayPauseButton(isPlaying: Boolean) {
        val iconRes = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        binding.btnPlayPause.setImageResource(iconRes)
    }

    /**
     * Actualiza el botón de shuffle.
     */
    private fun updateShuffleButton() {
        val tintColor = if (isShuffleEnabled) R.color.primary else R.color.text_secondary
        binding.btnShuffle.setColorFilter(
            resources.getColor(tintColor, null)
        )
    }

    /**
     * Actualiza el botón de repeat.
     */
    private fun updateRepeatButton() {
        val tintColor = if (isRepeatEnabled) R.color.primary else R.color.text_secondary
        binding.btnRepeat.setColorFilter(
            resources.getColor(tintColor, null)
        )
    }

    /**
     * Actualiza el botón de favorito.
     */
    private fun updateFavoriteButton() {
        val iconRes = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        val tintColor = if (isFavorite) R.color.error else R.color.text_primary
        binding.btnFavorite.setImageResource(iconRes)
        binding.btnFavorite.setColorFilter(
            resources.getColor(tintColor, null)
        )
    }

    // ========================================================================
    // AudioPlayerManager.AudioPlayerListener Implementation
    // ========================================================================

    override fun onTrackChanged(track: Track) {
        currentTrack = track
        updateUI(track)
    }

    override fun onPlaybackStateChanged(isPlaying: Boolean) {
        updatePlayPauseButton(isPlaying)
    }

    override fun onProgressUpdate(position: Long, duration: Long) {
        // Solo actualizar si el usuario no está arrastrando
        if (!isUserSeeking && duration > 0) {
            val progress = ((position.toFloat() / duration.toFloat()) * 100).toInt()
            binding.progressSeekbar.progress = progress
            binding.currentTime.text = AudioPlayerManager.formatTime(position)
        }
    }

    override fun onTrackCompleted() {
        // El AudioPlayerManager maneja la reproducción del siguiente track
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
