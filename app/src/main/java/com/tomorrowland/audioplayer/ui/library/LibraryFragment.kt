package com.tomorrowland.audioplayer.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tomorrowland.audioplayer.databinding.FragmentLibraryBinding

/**
 * ============================================================================
 * Tomorrowland Audio Player - Library Fragment
 * ============================================================================
 *
 * 📚 LIBRARY FRAGMENT:
 *
 * Esta pantalla es un placeholder para la biblioteca del usuario.
 * Los alumnos pueden expandirla para implementar:
 * - Lista de canciones favoritas
 * - Playlists personalizadas
 * - Historial de reproducción
 *
 * 📝 EJERCICIO PROPUESTO:
 * 1. Usar TrackRepository.getFavoriteTracks()
 * 2. Mostrar los favoritos en un RecyclerView
 * 3. Permitir crear nuevas playlists
 */
class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Implementar funcionalidad de biblioteca
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
