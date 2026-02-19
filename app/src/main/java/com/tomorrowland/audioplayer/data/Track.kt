package com.tomorrowland.audioplayer.data

/**
 * ============================================================================
 * Tomorrowland Audio Player - Modelo de Datos: Track
 * ============================================================================
 *
 * 🎵 DATA CLASS:
 *
 * En Kotlin, una "data class" es una clase especial diseñada para almacenar datos.
 * Automáticamente genera:
 * - equals(): Compara objetos por sus propiedades
 * - hashCode(): Genera hash único basado en propiedades
 * - toString(): Representación en texto del objeto
 * - copy(): Crea una copia con posibles modificaciones
 * - componentN(): Permite desestructuración (val (id, title) = track)
 *
 * 📝 USO:
 * ```kotlin
 * val track = Track(
 *     id = 1,
 *     title = "Pura Vida",
 *     artist = "Armin van Buuren",
 *     duration = "3:45",
 *     albumArtUrl = "https://i.ytimg.com/vi/tyLESVC4eSo/hqdefault.jpg",
 *     audioUrl = "https://i.ytimg.com/vi/wwrXjA5E7rw/hqdefault.jpg"
 * )
 * ```
 */
data class Track(
    /**
     * Identificador único del track.
     * Usado para identificar tracks en listas y navegación.
     */
    val id: Int,

    /**
     * Título de la canción.
     * Ejemplo: "Pura Vida"
     */
    val title: String,

    /**
     * Nombre del artista o DJ.
     * Ejemplo: "Armin van Buuren"
     */
    val artist: String,

    /**
     * Duración formateada como string.
     * Ejemplo: "3:45" (3 minutos, 45 segundos)
     */
    val duration: String,

    /**
     * Duración en milisegundos.
     * Usado para el SeekBar y cálculos de progreso.
     * Ejemplo: 225000 (3:45 en ms)
     */
    val durationMs: Long,

    /**
     * URL de la imagen del álbum o thumbnail.
     * Puede ser una URL de red o un recurso local.
     */
    val albumArtUrl: String,

    /**
     * URL del archivo de audio para streaming.
     * Puede ser un archivo MP3, stream de YouTube, etc.
     */
    val audioUrl: String,

    /**
     * Badge opcional que indica el tipo de fuente.
     * Ejemplos: "JSON_SRC", "YT_DATA_V3", "LEGACY_IO", "320KBPS"
     * Puede ser null si no tiene badge.
     */
    val badge: String? = null,

    /**
     * Indica si el track está marcado como favorito.
     * Por defecto es false.
     */
    val isFavorite: Boolean = false
)
