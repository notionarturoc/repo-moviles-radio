# 🎵 Tomorrowland Audio Player

**Proyecto Educativo para Ingeniería en Sistemas - Grupo H8B**

![Material Design 3](https://img.shields.io/badge/Material%20Design-3-6750A4)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-7F52FF)
![API Level](https://img.shields.io/badge/API-24%2B-brightgreen)

---

## 📱 Descripción

Tomorrowland Audio Player es una aplicación de reproductor de música nativa para Android, desarrollada con **Kotlin** y **XML**. El proyecto está diseñado como material educativo para enseñar los conceptos fundamentales del desarrollo de aplicaciones Android modernas.

### 🎯 Objetivos de Aprendizaje

- ✅ Manejo de listas de objetos (RecyclerView + Adapter)
- ✅ Estructuración de UI moderna con Material Design 3
- ✅ Navegación entre pantallas con Navigation Component
- ✅ Reproducción de audio con MediaPlayer
- ✅ Patrón de arquitectura con Repository
- ✅ View Binding para acceso seguro a vistas

---

## 📸 Capturas de Pantalla

| Pantalla Home | Now Playing |
|---------------|-------------|
| Lista de tracks con playlist Tomorrowland | Reproductor completo con controles |

---

## 🚀 Cómo Abrir el Proyecto

### Requisitos Previos

1. **Android Studio** Hedgehog (2023.1.1) o superior
2. **JDK 17** (incluido con Android Studio)
3. **Android SDK** API Level 34 (Android 14)
4. **Kotlin Plugin** 1.9.20+

### Pasos para Importar

1. **Descargar/Clonar el proyecto**
   ```bash
   # Si tienes Git instalado:
   git clone <url-del-repositorio>
   
   # O descarga el ZIP y descomprímelo
   ```

2. **Abrir en Android Studio**
   - Abre Android Studio
   - Selecciona `File > Open`
   - Navega hasta la carpeta `TomorrowlandAudioPlayer`
   - Haz clic en `OK`

3. **Sincronizar Gradle**
   - Android Studio detectará automáticamente el proyecto
   - Haz clic en `Sync Now` cuando aparezca el mensaje
   - Espera a que se descarguen las dependencias

4. **Ejecutar la aplicación**
   - Conecta un dispositivo Android o inicia un emulador
   - Haz clic en el botón ▶️ `Run` o presiona `Shift + F10`

---

## 📁 Estructura del Proyecto

```
TomorrowlandAudioPlayer/
├── app/
│   ├── src/main/
│   │   ├── java/com/tomorrowland/audioplayer/
│   │   │   ├── MainActivity.kt           # Actividad principal
│   │   │   ├── data/
│   │   │   │   ├── Track.kt              # Modelo de datos
│   │   │   │   └── TrackRepository.kt    # Repositorio de tracks
│   │   │   ├── ui/
│   │   │   │   ├── home/
│   │   │   │   │   ├── HomeFragment.kt   # Pantalla principal
│   │   │   │   │   └── TrackAdapter.kt   # Adapter del RecyclerView
│   │   │   │   ├── nowplaying/
│   │   │   │   │   └── NowPlayingFragment.kt  # Reproductor completo
│   │   │   │   ├── search/
│   │   │   │   ├── library/
│   │   │   │   └── profile/
│   │   │   └── utils/
│   │   │       └── AudioPlayerManager.kt # Gestor de reproducción
│   │   ├── res/
│   │   │   ├── layout/                   # Layouts XML
│   │   │   ├── navigation/               # Navigation Graph
│   │   │   ├── menu/                     # Menús
│   │   │   ├── values/                   # Colores, strings, themes
│   │   │   ├── drawable/                 # Iconos y backgrounds
│   │   │   └── anim/                     # Animaciones
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts                  # Configuración del módulo
├── build.gradle.kts                      # Configuración del proyecto
├── settings.gradle.kts
└── README.md
```

---

## 🎨 Colores del Tema Tomorrowland

| Color | Código | Uso |
|-------|--------|-----|
| 🔵 Primary | `#135bec` | Color principal (azul vibrante) |
| 🟣 Secondary | `#7c3aed` | Acentos violeta |
| 🟡 Tertiary | `#fbbf24` | Acentos dorados |
| ⬛ Background | `#101622` | Fondo oscuro |
| ⬜ Surface | `#1a1f2e` | Tarjetas y paneles |

---

## 🎵 Tracks Incluidos

| # | Título | Artista |
|---|--------|---------|
| 1 | Pura Vida | Armin van Buuren |
| 2 | High on Life | Martin Garrix |
| 3 | Mammoth | Dimitri Vegas & Like Mike |
| 4 | Innerbloom | RÜFÜS DU SOL |
| 5 | Adagio for Strings | Tiësto |

---

## 📚 Conceptos Implementados

### 1. View Binding
```kotlin
// En lugar de findViewById
private lateinit var binding: FragmentHomeBinding

override fun onCreateView(...): View {
    binding = FragmentHomeBinding.inflate(inflater)
    return binding.root
}
```

### 2. RecyclerView con ListAdapter
```kotlin
class TrackAdapter : ListAdapter<Track, TrackViewHolder>(DiffCallback()) {
    // Usa DiffUtil para actualizaciones eficientes
}
```

### 3. Navigation Component
```kotlin
// Navegación type-safe con Safe Args
findNavController().navigate(
    HomeFragmentDirections.actionHomeToNowPlaying(track.id)
)
```

### 4. Data Class
```kotlin
data class Track(
    val id: Int,
    val title: String,
    val artist: String,
    // Autogenera equals(), hashCode(), toString(), copy()
)
```

### 5. Object (Singleton)
```kotlin
object AudioPlayerManager {
    // Una sola instancia para toda la app
    private var mediaPlayer: MediaPlayer? = null
}
```

---

## 🛠️ Dependencias Principales

| Librería | Versión | Uso |
|----------|---------|-----|
| Material Components | 1.11.0 | Material Design 3 |
| Navigation | 2.7.6 | Navegación entre fragments |
| Lifecycle | 2.7.0 | ViewModel y LiveData |
| Glide | 4.16.0 | Carga de imágenes |
| Media3 ExoPlayer | 1.2.0 | Reproducción de audio |

---

## 📝 Ejercicios Propuestos

### Nivel Básico
1. **Cambiar colores del tema**: Modifica `colors.xml` con tu paleta personalizada
2. **Agregar más tracks**: Añade nuevos tracks en `TrackRepository.kt`
3. **Personalizar strings**: Traduce la app al inglés en `values-en/strings.xml`

### Nivel Intermedio
4. **Implementar búsqueda**: Completa `SearchFragment` con filtrado de tracks
5. **Guardar favoritos**: Usa SharedPreferences para persistir favoritos
6. **Agregar animaciones**: Implementa transiciones entre pantallas

### Nivel Avanzado
7. **Integrar API real**: Conecta con una API de música (Spotify, YouTube)
8. **Implementar Room Database**: Persiste los tracks localmente
9. **Agregar notificación de reproducción**: MediaSession + Notification

---

## ⚠️ Solución de Problemas

### Error: "SDK location not found"
1. Crea el archivo `local.properties` en la raíz del proyecto
2. Agrega: `sdk.dir=/ruta/a/tu/Android/sdk`

### Error: "Gradle sync failed"
1. `File > Invalidate Caches and Restart`
2. Intenta sincronizar de nuevo

### Error: "No emulator/device found"
1. Abre AVD Manager
2. Crea un emulador con API 24+
3. Inicia el emulador antes de ejecutar

---

## 👨‍🏫 Información del Curso

- **Materia**: Desarrollo de Aplicaciones Móviles
- **Grupo**: H8B
- **Proyecto Base**: Google Stitch - Audio Player Playback
- **Tema**: Tomorrowland 2024

---

## 📄 Licencia

Este proyecto es material educativo y puede ser utilizado libremente para propósitos de aprendizaje.

---

**¡Disfruta programando y que la música de Tomorrowland te inspire! 🎧🎉**
