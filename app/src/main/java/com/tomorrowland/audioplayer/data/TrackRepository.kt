package com.tomorrowland.audioplayer.data

/**
 * ============================================================================
 * Tomorrowland Audio Player - Repositorio de Tracks
 * ============================================================================
 *
 * 📁 PATRÓN REPOSITORY:
 *
 * El patrón Repository actúa como intermediario entre la lógica de negocio
 * y las fuentes de datos (API, Base de datos, etc.).
 *
 * BENEFICIOS:
 * 1. Separa la lógica de obtención de datos de la UI
 * 2. Facilita pruebas unitarias (mock del repositorio)
 * 3. Permite cambiar la fuente de datos sin modificar la UI
 * 4. Centraliza la gestión de caché y actualizaciones
 *
 * 🔒 OBJECT (SINGLETON):
 *
 * En Kotlin, "object" crea una instancia única (Singleton).
 * Esto garantiza que solo exista una fuente de verdad para los tracks.
 *
 * 🎯 EN UNA APP REAL:
 * - Los datos vendrían de una API REST (Retrofit)
 * - Se guardarían en una base de datos local (Room)
 * - Se usaría Flow o LiveData para datos reactivos
 */
object TrackRepository {

    /**
     * Lista de tracks del festival Tomorrowland 2024.
     *
     * Estos son los 5 tracks principales de la playlist educativa.
     * En una app real, estos datos vendrían de una API o base de datos.
     *
     * 🎵 URLs de audio:
     * Las URLs son ejemplos de audio de dominio público.
     * En producción, usarías URLs de tu servidor o servicio de streaming.
     */
    private val tracks = listOf(
        Track(
            id = 1,
            title = "Pura Vida",
            artist = "Armin van Buuren",
            duration = "3:45",
            durationMs = 225000, // 3:45 en milisegundos
            albumArtUrl = "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=400",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            badge = "JSON_SRC, 320KBPS",
            isFavorite = false
        ),
        Track(
            id = 2,
            title = "High on Life",
            artist = "Martin Garrix",
            duration = "3:23",
            durationMs = 203000,
            albumArtUrl = "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=400",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
            badge = "YT_DATA_V3",
            isFavorite = false
        ),
        Track(
            id = 3,
            title = "Mammoth",
            artist = "Dimitri Vegas & Like Mike",
            duration = "4:12",
            durationMs = 252000,
            albumArtUrl = "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=400",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
            badge = "LEGACY_IO",
            isFavorite = true
        ),
        Track(
            id = 4,
            title = "Innerbloom",
            artist = "RÜFÜS DU SOL",
            duration = "5:48",
            durationMs = 348000,
            albumArtUrl = "https://images.unsplash.com/photo-1506157786151-b8491531f063?w=400",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
            badge = null,
            isFavorite = false
        ),
        Track(
            id = 5,
            title = "Adagio for Strings",
            artist = "Tiësto",
            duration = "4:35",
            durationMs = 275000,
            albumArtUrl = "https://images.unsplash.com/photo-1429962714451-bb934ecdc4ec?w=400",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3",
            badge = null,
            isFavorite = true
        )
    )

    /**
     * Obtiene todos los tracks de la playlist.
     *
     * @return Lista inmutable de todos los tracks
     *
     * 📝 NOTA:
     * Retorna una copia de la lista para evitar modificaciones externas.
     * Esto es una buena práctica para mantener la inmutabilidad.
     */
    fun getAllTracks(): List<Track> = tracks.toList()

    /**
     * Obtiene un track por su ID.
     *
     * @param id El identificador único del track
     * @return El track encontrado o null si no existe
     *
     * 📝 NOTA:
     * Usa la función find() de Kotlin que retorna el primer elemento
     * que cumple con la condición, o null si no encuentra ninguno.
     */
    fun getTrackById(id: Int): Track? = tracks.find { it.id == id }

    /**
     * Obtiene el número total de tracks.
     *
     * @return Número de tracks en la playlist
     */
    fun getTrackCount(): Int = tracks.size

    /**
     * Obtiene el siguiente track en la playlist.
     *
     * @param currentId ID del track actual
     * @return El siguiente track, o el primero si estamos en el último
     *
     * 📝 COMPORTAMIENTO CIRCULAR:
     * Si el track actual es el último, retorna el primero (loop).
     */
    fun getNextTrack(currentId: Int): Track {
        val currentIndex = tracks.indexOfFirst { it.id == currentId }
        val nextIndex = (currentIndex + 1) % tracks.size
        return tracks[nextIndex]
    }

    /**
     * Obtiene el track anterior en la playlist.
     *
     * @param currentId ID del track actual
     * @return El track anterior, o el último si estamos en el primero
     *
     * 📝 COMPORTAMIENTO CIRCULAR:
     * Si el track actual es el primero, retorna el último (loop).
     */
    fun getPreviousTrack(currentId: Int): Track {
        val currentIndex = tracks.indexOfFirst { it.id == currentId }
        val previousIndex = if (currentIndex <= 0) tracks.size - 1 else currentIndex - 1
        return tracks[previousIndex]
    }

    /**
     * Obtiene los tracks marcados como favoritos.
     *
     * @return Lista de tracks favoritos
     */
    fun getFavoriteTracks(): List<Track> = tracks.filter { it.isFavorite }
}
