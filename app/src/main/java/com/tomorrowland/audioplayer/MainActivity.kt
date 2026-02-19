package com.tomorrowland.audioplayer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.tomorrowland.audioplayer.data.Track
import com.tomorrowland.audioplayer.databinding.ActivityMainBinding
import com.tomorrowland.audioplayer.databinding.LayoutMiniPlayerBinding
import com.tomorrowland.audioplayer.utils.AudioPlayerManager

/**
 * ============================================================================
 * Tomorrowland Audio Player - MainActivity
 * ============================================================================
 *
 * 🏠 ACTIVIDAD PRINCIPAL:
 *
 * Esta es la única Activity de la aplicación (patrón Single Activity).
 * Contiene:
 * 1. NavHostFragment: Contenedor donde se cargan los fragments
 * 2. BottomNavigationView: Navegación principal
 * 3. Mini Player: Reproductor flotante
 *
 * 📝 CONCEPTOS CLAVE:
 *
 * 1. VIEW BINDING:
 *    - Genera clases de binding automáticamente
 *    - Acceso type-safe a las vistas (sin findViewById)
 *    - Evita NullPointerException y ClassCastException
 *
 * 2. NAVIGATION COMPONENT:
 *    - Gestiona la navegación entre fragments
 *    - Maneja el back stack automáticamente
 *    - Integración con BottomNavigationView
 *
 * 3. PATRÓN OBSERVER:
 *    - MainActivity escucha los eventos del AudioPlayerManager
 *    - Actualiza el Mini Player cuando cambia el estado
 */
class MainActivity : AppCompatActivity(), AudioPlayerManager.AudioPlayerListener {

    // === View Binding ===
    // Se genera automáticamente a partir del layout activity_main.xml
    private lateinit var binding: ActivityMainBinding
    private lateinit var miniPlayerBinding: LayoutMiniPlayerBinding

    // === Navigation Controller ===
    private lateinit var navController: NavController

    /**
     * Método onCreate: Se llama cuando se crea la Activity.
     *
     * 📝 CICLO DE VIDA:
     * onCreate -> onStart -> onResume -> (app en uso)
     * -> onPause -> onStop -> onDestroy
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // === Inflar el layout usando View Binding ===
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // === Obtener binding del Mini Player (incluido en activity_main) ===
        miniPlayerBinding = LayoutMiniPlayerBinding.bind(binding.miniPlayerContainer.root)

        // === Configurar Navigation Component ===
        setupNavigation()

        // === Configurar Mini Player ===
        setupMiniPlayer()

        // === Registrar como listener del AudioPlayerManager ===
        AudioPlayerManager.setListener(this)
    }

    /**
     * Configura el Navigation Component con el BottomNavigationView.
     *
     * 📝 NAVIGATION COMPONENT:
     * - NavHostFragment: Contenedor de los fragments
     * - NavController: Controla la navegación
     * - setupWithNavController(): Conecta BottomNav con el NavController
     */
    private fun setupNavigation() {
        // Obtener el NavHostFragment del layout
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Obtener el NavController
        navController = navHostFragment.navController

        // Conectar BottomNavigationView con NavController
        // Esto hace que al presionar un tab, se navegue al fragment correspondiente
        binding.bottomNavigation.setupWithNavController(navController)

        // Listener para cambios de destino (opcional)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Ocultar BottomNavigation en la pantalla NowPlaying
            when (destination.id) {
                R.id.nowPlayingFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                    binding.miniPlayerContainer.root.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    // Mostrar mini player solo si hay un track seleccionado
                    if (AudioPlayerManager.currentTrack != null) {
                        binding.miniPlayerContainer.root.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    /**
     * Configura el Mini Player y sus controles.
     *
     * 📝 MINI PLAYER:
     * - Muestra información del track actual
     * - Permite controles básicos (play/pause, next, previous)
     * - Al hacer tap, navega al reproductor completo
     */
    private fun setupMiniPlayer() {
        // Tap en el mini player -> ir a Now Playing
        miniPlayerBinding.miniPlayerRoot.setOnClickListener {
            AudioPlayerManager.currentTrack?.let {
                navController.navigate(R.id.nowPlayingFragment)
            }
        }

        // Botón Play/Pause
        miniPlayerBinding.miniBtnPlayPause.setOnClickListener {
            AudioPlayerManager.togglePlayPause()
        }

        // Botón Previous
        miniPlayerBinding.miniBtnPrevious.setOnClickListener {
            AudioPlayerManager.playPrevious(this)
        }

        // Botón Next
        miniPlayerBinding.miniBtnNext.setOnClickListener {
            AudioPlayerManager.playNext(this)
        }
    }

    /**
     * Actualiza la UI del Mini Player con la información del track.
     *
     * @param track El track actual
     */
    private fun updateMiniPlayer(track: Track) {
        miniPlayerBinding.apply {
            miniTrackTitle.text = track.title
            miniTrackArtist.text = track.artist

            // Cargar imagen usando Glide
            Glide.with(this@MainActivity)
                .load(track.albumArtUrl)
                .placeholder(R.drawable.ic_placeholder_album)
                .circleCrop()
                .into(miniTrackImage)
        }

        // Mostrar el mini player
        binding.miniPlayerContainer.root.visibility = View.VISIBLE
    }

    /**
     * Actualiza el icono de play/pause según el estado.
     *
     * @param isPlaying true si está reproduciendo, false si está pausado
     */
    private fun updatePlayPauseButton(isPlaying: Boolean) {
        val iconRes = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        miniPlayerBinding.miniBtnPlayPause.setImageResource(iconRes)
    }

    // ========================================================================
    // AudioPlayerManager.AudioPlayerListener Implementation
    // ========================================================================

    /**
     * Callback: Se llama cuando cambia el track actual.
     */
    override fun onTrackChanged(track: Track) {
        updateMiniPlayer(track)
    }

    /**
     * Callback: Se llama cuando cambia el estado de reproducción.
     */
    override fun onPlaybackStateChanged(isPlaying: Boolean) {
        updatePlayPauseButton(isPlaying)
    }

    /**
     * Callback: Se llama con actualizaciones de progreso.
     */
    override fun onProgressUpdate(position: Long, duration: Long) {
        // Actualizar la barra de progreso del mini player
        if (duration > 0) {
            val progress = ((position.toFloat() / duration.toFloat()) * 100).toInt()
            miniPlayerBinding.miniProgressBar.progress = progress
        }
    }

    /**
     * Callback: Se llama cuando el track termina.
     */
    override fun onTrackCompleted() {
        // El AudioPlayerManager ya maneja la reproducción del siguiente track
    }

    /**
     * Método onDestroy: Se llama cuando se destruye la Activity.
     *
     * ⚠️ IMPORTANTE:
     * Liberar los recursos del AudioPlayerManager para evitar memory leaks.
     */
    override fun onDestroy() {
        super.onDestroy()
        AudioPlayerManager.setListener(null)
    }
}
