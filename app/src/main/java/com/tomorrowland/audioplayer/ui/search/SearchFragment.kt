package com.tomorrowland.audioplayer.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tomorrowland.audioplayer.databinding.FragmentSearchBinding

/**
 * ============================================================================
 * Tomorrowland Audio Player - Search Fragment
 * ============================================================================
 *
 * 🔍 SEARCH FRAGMENT:
 *
 * Esta pantalla es un placeholder para la funcionalidad de búsqueda.
 * Los alumnos pueden expandirla para implementar:
 * - Búsqueda por título o artista
 * - Filtros por género
 * - Integración con APIs de música
 *
 * 📝 EJERCICIO PROPUESTO:
 * 1. Agregar un SearchView en el layout
 * 2. Filtrar los tracks del TrackRepository
 * 3. Mostrar los resultados en un RecyclerView
 */
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Implementar funcionalidad de búsqueda
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
