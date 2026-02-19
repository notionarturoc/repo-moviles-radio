package com.tomorrowland.audioplayer.utils

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.tomorrowland.audioplayer.data.Track
import com.tomorrowland.audioplayer.data.TrackRepository

/**
 * ============================================================================
 * Tomorrowland Audio Player - Gestor de Reproducción de Audio
 * ============================================================================
 *
 * 🎵 AUDIOPLAYER MANAGER:
 *
 * Este singleton gestiona la reproducción de audio en toda la aplicación.
 * Usa MediaPlayer de Android para la reproducción básica.
 *
 * 📝 CONCEPTOS CLAVE:
 *
 * 1. SINGLETON: Una sola instancia para toda la app.
 *    Garantiza que solo haya un reproductor activo.
 *
 * 2. MEDIAPLAYER: Clase de Android para reproducir audio/video.
 *    - prepareAsync(): Prepara el audio en segundo plano
 *    - start(): Inicia la reproducción
 *    - pause(): Pausa la reproducción
 *    - seekTo(): Salta a una posición específica
 *    - release(): Libera recursos (MUY IMPORTANTE)
 *
 * 3. CALLBACK PATTERN: Notifica a la UI sobre cambios de estado.
 *
 * ⚠️ EN PRODUCCIÓN:
 * Para una app profesional, considera usar ExoPlayer (Media3)
 * que ofrece mejor soporte para streaming, DRM, etc.
 */
object AudioPlayerManager {

    // === MediaPlayer instance ===
    private var mediaPlayer: MediaPlayer? = null

    // === Estado actual ===
    private var _currentTrack: Track? = null
    val currentTrack: Track? get() = _currentTrack

    private var _isPlaying: Boolean = false
    val isPlaying: Boolean get() = _isPlaying

    private var _currentPosition: Long = 0L
    val currentPosition: Long get() = _currentPosition

    // === Handler para actualizar el progreso ===
    private val handler = Handler(Looper.getMainLooper())
    private var progressRunnable: Runnable? = null

    // === Listener para notificar cambios a la UI ===
    private var listener: AudioPlayerListener? = null

    /**
     * Interface para comunicar eventos del reproductor a la UI.
     *
     * 📝 PATRÓN OBSERVER:
     * La UI se registra como listener y recibe notificaciones
     * cuando cambia el estado del reproductor.
     */
    interface AudioPlayerListener {
        /** Se llama cuando cambia el track actual */
        fun onTrackChanged(track: Track)

        /** Se llama cuando cambia el estado de reproducción */
        fun onPlaybackStateChanged(isPlaying: Boolean)

        /** Se llama periódicamente con el progreso actual */
        fun onProgressUpdate(position: Long, duration: Long)

        /** Se llama cuando el track actual termina */
        fun onTrackCompleted()
    }

    /**
     * Establece el listener para recibir eventos.
     *
     * @param listener El listener que recibirá los eventos
     */
    fun setListener(listener: AudioPlayerListener?) {
        this.listener = listener
    }

    /**
     * Reproduce un track específico.
     *
     * @param context Contexto de Android
     * @param track El track a reproducir
     *
     * 📝 PROCESO:
     * 1. Libera el MediaPlayer anterior si existe
     * 2. Crea un nuevo MediaPlayer
     * 3. Configura la URL del audio
     * 4. Prepara el audio de forma asíncrona
     * 5. Inicia la reproducción cuando está listo
     */
    fun playTrack(context: Context, track: Track) {
        // Liberar el reproductor anterior
        releasePlayer()

        _currentTrack = track
        _currentPosition = 0L

        // Notificar el cambio de track
        listener?.onTrackChanged(track)

        try {
            // Crear nuevo MediaPlayer
            mediaPlayer = MediaPlayer().apply {
                // Configurar la fuente de audio
                setDataSource(track.audioUrl)

                // Listener cuando el audio está listo
                setOnPreparedListener {
                    _isPlaying = true
                    start()
                    listener?.onPlaybackStateChanged(true)
                    startProgressUpdates()
                }

                // Listener cuando termina el audio
                setOnCompletionListener {
                    _isPlaying = false
                    listener?.onPlaybackStateChanged(false)
                    listener?.onTrackCompleted()
                    stopProgressUpdates()
                    // Automáticamente reproducir el siguiente
                    playNext(context)
                }

                // Listener de errores
                setOnErrorListener { _, what, extra ->
                    android.util.Log.e("AudioPlayerManager", "Error: what=$what, extra=$extra")
                    _isPlaying = false
                    listener?.onPlaybackStateChanged(false)
                    true // Error manejado
                }

                // Preparar de forma asíncrona (no bloquea el hilo principal)
                prepareAsync()
            }
        } catch (e: Exception) {
            android.util.Log.e("AudioPlayerManager", "Error al reproducir: ${e.message}")
        }
    }

    /**
     * Alterna entre reproducir y pausar.
     */
    fun togglePlayPause() {
        mediaPlayer?.let { player ->
            if (_isPlaying) {
                player.pause()
                _isPlaying = false
                stopProgressUpdates()
            } else {
                player.start()
                _isPlaying = true
                startProgressUpdates()
            }
            listener?.onPlaybackStateChanged(_isPlaying)
        }
    }

    /**
     * Pausa la reproducción.
     */
    fun pause() {
        mediaPlayer?.let { player ->
            if (_isPlaying) {
                player.pause()
                _isPlaying = false
                stopProgressUpdates()
                listener?.onPlaybackStateChanged(false)
            }
        }
    }

    /**
     * Reanuda la reproducción.
     */
    fun resume() {
        mediaPlayer?.let { player ->
            if (!_isPlaying) {
                player.start()
                _isPlaying = true
                startProgressUpdates()
                listener?.onPlaybackStateChanged(true)
            }
        }
    }

    /**
     * Salta a una posición específica en el audio.
     *
     * @param position Posición en milisegundos
     */
    fun seekTo(position: Long) {
        mediaPlayer?.seekTo(position.toInt())
        _currentPosition = position
    }

    /**
     * Reproduce el siguiente track en la playlist.
     *
     * @param context Contexto de Android
     */
    fun playNext(context: Context) {
        _currentTrack?.let { current ->
            val nextTrack = TrackRepository.getNextTrack(current.id)
            playTrack(context, nextTrack)
        }
    }

    /**
     * Reproduce el track anterior en la playlist.
     *
     * @param context Contexto de Android
     */
    fun playPrevious(context: Context) {
        _currentTrack?.let { current ->
            val previousTrack = TrackRepository.getPreviousTrack(current.id)
            playTrack(context, previousTrack)
        }
    }

    /**
     * Inicia las actualizaciones de progreso.
     *
     * 📝 HANDLER + RUNNABLE:
     * Usamos un Handler para ejecutar código en el hilo principal
     * de forma periódica (cada 500ms).
     */
    private fun startProgressUpdates() {
        progressRunnable = object : Runnable {
            override fun run() {
                mediaPlayer?.let { player ->
                    if (_isPlaying) {
                        _currentPosition = player.currentPosition.toLong()
                        val duration = player.duration.toLong()
                        listener?.onProgressUpdate(_currentPosition, duration)
                    }
                }
                // Programar la siguiente actualización en 500ms
                handler.postDelayed(this, 500)
            }
        }
        progressRunnable?.let { handler.post(it) }
    }

    /**
     * Detiene las actualizaciones de progreso.
     */
    private fun stopProgressUpdates() {
        progressRunnable?.let { handler.removeCallbacks(it) }
        progressRunnable = null
    }

    /**
     * Libera los recursos del MediaPlayer.
     *
     * ⚠️ MUY IMPORTANTE:
     * Siempre liberar el MediaPlayer cuando no se necesita
     * para evitar fugas de memoria y recursos.
     */
    private fun releasePlayer() {
        stopProgressUpdates()
        mediaPlayer?.apply {
            stop()
            reset()
            release()
        }
        mediaPlayer = null
    }

    /**
     * Libera todos los recursos.
     * Llamar cuando la app se cierra o el reproductor ya no es necesario.
     */
    fun release() {
        releasePlayer()
        _currentTrack = null
        _isPlaying = false
        _currentPosition = 0L
        listener = null
    }

    /**
     * Formatea milisegundos a formato mm:ss.
     *
     * @param ms Tiempo en milisegundos
     * @return String formateado como "3:45"
     *
     * 📝 EJEMPLO:
     * formatTime(225000) -> "3:45"
     */
    fun formatTime(ms: Long): String {
        val totalSeconds = ms / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }
}
