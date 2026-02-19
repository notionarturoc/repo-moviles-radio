package com.tomorrowland.audioplayer.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tomorrowland.audioplayer.R
import com.tomorrowland.audioplayer.data.Track
import com.tomorrowland.audioplayer.databinding.ItemTrackBinding

/**
 * ============================================================================
 * Tomorrowland Audio Player - Track Adapter
 * ============================================================================
 *
 * 📝 RECYCLERVIEW ADAPTER:
 *
 * El Adapter es el puente entre los datos y el RecyclerView.
 * Se encarga de:
 * 1. Crear las vistas (ViewHolders)
 * 2. Vincular los datos a las vistas
 * 3. Notificar cambios en los datos
 *
 * 🌟 LISTADAPTER:
 *
 * ListAdapter es una implementación moderna de RecyclerView.Adapter que:
 * - Usa DiffUtil para calcular cambios de forma eficiente
 * - Anima automáticamente las inserciones/eliminaciones
 * - Simplifica el manejo de listas
 *
 * 📌 PATRÓN VIEWHOLDER:
 *
 * ViewHolder almacena referencias a las vistas de cada item.
 * Esto evita llamar a findViewById() repetidamente, mejorando el rendimiento.
 */
class TrackAdapter(
    private val onTrackClicked: (Track) -> Unit
) : ListAdapter<Track, TrackAdapter.TrackViewHolder>(TrackDiffCallback()) {

    /**
     * onCreateViewHolder: Crea un nuevo ViewHolder.
     *
     * Se llama cuando el RecyclerView necesita un nuevo ViewHolder
     * (no hay ViewHolders reciclados disponibles).
     *
     * @param parent El ViewGroup padre (el RecyclerView)
     * @param viewType Tipo de vista (para múltiples tipos de items)
     * @return Nuevo ViewHolder con el layout inflado
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        // Inflar el layout usando View Binding
        val binding = ItemTrackBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrackViewHolder(binding)
    }

    /**
     * onBindViewHolder: Vincula datos a un ViewHolder existente.
     *
     * Se llama para mostrar datos en una posición específica.
     * El ViewHolder puede ser nuevo o reciclado.
     *
     * @param holder El ViewHolder a vincular
     * @param position Posición del item en la lista
     */
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track, position + 1)
    }

    /**
     * ViewHolder para cada item de track.
     *
     * 📝 INNER CLASS:
     * Es una clase interna que tiene acceso al onTrackClicked del Adapter.
     */
    inner class TrackViewHolder(
        private val binding: ItemTrackBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Vincula los datos del track a las vistas.
         *
         * @param track El track a mostrar
         * @param trackNumber Número del track en la lista (1-based)
         */
        fun bind(track: Track, trackNumber: Int) {
            binding.apply {
                // Número del track
                this.trackNumber.text = trackNumber.toString()

                // Título y artista
                trackTitle.text = track.title
                trackArtist.text = track.artist

                // Duración
                trackDuration.text = track.duration

                // Badge (opcional)
                if (track.badge != null) {
                    trackBadge.visibility = View.VISIBLE
                    trackBadge.text = track.badge
                } else {
                    trackBadge.visibility = View.GONE
                }

                // Cargar imagen con Glide
                // Glide maneja:
                // - Caché en memoria y disco
                // - Descarga asíncrona
                // - Transformaciones (circleCrop, centerCrop, etc.)
                // - Placeholders mientras carga
                Glide.with(itemView.context)
                    .load(track.albumArtUrl)
                    .placeholder(R.drawable.ic_placeholder_album)
                    .circleCrop() // Hacer la imagen circular
                    .into(trackThumbnail)

                // Click en todo el item
                root.setOnClickListener {
                    onTrackClicked(track)
                }

                // Click en el botón de play
                btnPlay.setOnClickListener {
                    onTrackClicked(track)
                }
            }
        }
    }

    /**
     * DiffUtil Callback para calcular diferencias entre listas.
     *
     * 📝 DIFFUTIL:
     *
     * DiffUtil compara dos listas y calcula las operaciones mínimas
     * necesarias para transformar una en otra.
     *
     * BENEFICIOS:
     * - Mejor rendimiento que notifyDataSetChanged()
     * - Animaciones automáticas de inserción/eliminación
     * - Actualiza solo los items que cambiaron
     *
     * MÉTODOS:
     * - areItemsTheSame: ¿Son el mismo item? (comparar IDs)
     * - areContentsTheSame: ¿Tienen el mismo contenido? (comparar datos)
     */
    class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {

        /**
         * Verifica si dos items representan el mismo objeto.
         * Normalmente se compara por ID único.
         */
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.id == newItem.id
        }

        /**
         * Verifica si el contenido de dos items es igual.
         * Se llama solo si areItemsTheSame() retorna true.
         *
         * Como Track es una data class, == compara todas las propiedades.
         */
        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }
}
