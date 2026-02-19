package com.tomorrowland.audioplayer.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tomorrowland.audioplayer.databinding.FragmentProfileBinding

/**
 * ============================================================================
 * Tomorrowland Audio Player - Profile Fragment
 * ============================================================================
 *
 * 👤 PROFILE FRAGMENT:
 *
 * Esta pantalla es un placeholder para el perfil del usuario.
 * Los alumnos pueden expandirla para implementar:
 * - Edición de perfil
 * - Configuración de la app
 * - Estadísticas de reproducción
 *
 * 📝 EJERCICIO PROPUESTO:
 * 1. Agregar SharedPreferences para guardar configuración
 * 2. Implementar tema claro/oscuro
 * 3. Agregar estadísticas de tiempo escuchado
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Implementar funcionalidad de perfil
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
