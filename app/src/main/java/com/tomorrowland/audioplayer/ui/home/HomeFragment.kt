package com.tomorrowland.audioplayer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tomorrowland.audioplayer.R
import com.tomorrowland.audioplayer.data.Track
import com.tomorrowland.audioplayer.data.TrackRepository
import com.tomorrowland.audioplayer.databinding.FragmentHomeBinding
import com.tomorrowland.audioplayer.utils.AudioPlayerManager

/**
 * ============================================================================
 * Tomorrowland Audio Player - Home Fragment (Pantalla Principal)
 * ============================================================================
 *
 * 🏠 HOME FRAGMENT:
 *
 * Esta es la pantalla principal que muestra la playlist de Tomorrowland.
 * Contiene:
 * 1. Header con efecto glassmorphism
 * 2. Hero Card con featured stream
 * 3. Lista de tracks (RecyclerView)
 *
 * 📝 CONCEPTOS CLAVE:
 *
 * 1. FRAGMENT:
 *    - Representa una porción reutilizable de UI
 *    - Tiene su propio ciclo de vida
 *    - Puede combinarse con otros fragments
 *
 * 2. VIEW BINDING EN FRAGMENTS:
 *    - Diferente a Activity: usar _binding nullable
 *    - Limpiar en onDestroyView para evitar memory leaks
 *
 * 3. RECYCLERVIEW:
 *    - Lista eficiente que recicla vistas
 *    - Necesita: LayoutManager + Adapter
 */
class HomeFragment : Fragment() {

    // === View Binding ===
    // En fragments, usamos _binding nullable para evitar memory leaks
    private var _binding: FragmentHomeBinding? = null
    // Propiedad de acceso seguro (solo válida entre onCreateView y onDestroyView)
    private val binding get() = _binding!!

    // === Adapter para el RecyclerView ===
    private lateinit var trackAdapter: TrackAdapter

    /**
     * onCreateView: Infla el layout del fragment.
     *
     * 📝 CICLO DE VIDA DEL FRAGMENT:
     * onAttach -> onCreate -> onCreateView -> onViewCreated
     * -> onStart -> onResume -> (fragment visible)
     * -> onPause -> onStop -> onDestroyView -> onDestroy -> onDetach
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * onViewCreated: Se llama después de que la vista está creada.
     * Aquí configuramos los listeners y cargamos los datos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView
        setupRecyclerView()

        // Configurar el Hero Card
        setupHeroCard()

        // Cargar los tracks
        loadTracks()
    }

    /**
     * Configura el RecyclerView con su LayoutManager y Adapter.
     *
     * 📝 RECYCLERVIEW COMPONENTS:
     *
     * 1. LayoutManager: Define cómo se organizan los items
     *    - LinearLayoutManager: Lista vertical u horizontal
     *    - GridLayoutManager: Cuadrícula
     *    - StaggeredGridLayoutManager: Cuadrícula con items de diferente tamaño
     *
     * 2. Adapter: Proporciona los datos y crea las vistas
     *    - onCreateViewHolder: Crea nuevas vistas
     *    - onBindViewHolder: Vincula datos a las vistas
     *    - getItemCount: Retorna el número de items
     */
    private fun setupRecyclerView() {
        // Crear el adapter con el callback para clicks
        trackAdapter = TrackAdapter { track ->
            onTrackClicked(track)
        }

        // Configurar el RecyclerView
        binding.tracksRecyclerView.apply {
            // LayoutManager: Lista vertical
            layoutManager = LinearLayoutManager(requireContext())

            // Asignar el adapter
            adapter = trackAdapter

            // Optimización: los items tienen tamaño fijo
            setHasFixedSize(true)
        }
    }

    /**
     * Configura el Hero Card con la imagen destacada.
     */
    private fun setupHeroCard() {
        // Cargar imagen del hero usando Glide
        Glide.with(this)
            .load("https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=800")
            .placeholder(R.drawable.bg_gradient_tomorrowland)
            .centerCrop()
            .into(binding.heroImage)

        // Click en el hero card
        binding.heroCard.setOnClickListener {
            // Reproducir el primer track
            val firstTrack = TrackRepository.getAllTracks().firstOrNull()
            firstTrack?.let { onTrackClicked(it) }
        }
    }

    /**
     * Carga los tracks del repositorio y los muestra en el RecyclerView.
     */
    private fun loadTracks() {
        // Obtener todos los tracks del repositorio
        val tracks = TrackRepository.getAllTracks()

        // Enviar los tracks al adapter
        trackAdapter.submitList(tracks)

        // Actualizar el badge con el número de tracks
        binding.tracksBadge.text = getString(R.string.tracks_count, tracks.size)
    }

    /**
     * Callback cuando se hace click en un track.
     *
     * @param track El track seleccionado
     *
     * 📝 FLUJO:
     * 1. Inicia la reproducción del track
     * 2. Navega a la pantalla NowPlaying
     */
    private fun onTrackClicked(track: Track) {
        // Iniciar reproducción
        AudioPlayerManager.playTrack(requireContext(), track)

        // Navegar a NowPlaying
        // Usamos Safe Args para pasar el ID del track
        val action = HomeFragmentDirections.actionHomeToNowPlaying(track.id)
        findNavController().navigate(action)
    }

    /**
     * onDestroyView: Se llama cuando la vista del fragment se destruye.
     *
     * ⚠️ MUY IMPORTANTE:
     * Limpiar el binding para evitar memory leaks.
     * El fragment puede sobrevivir a su vista (en el back stack).
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
