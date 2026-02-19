// ============================================================================
// Tomorrowland Audio Player - Build Gradle del Proyecto Raíz
// Proyecto educativo para Ingeniería en Sistemas
// ============================================================================

// Configuración de plugins a nivel de proyecto
plugins {
    // Plugin de Android para aplicaciones - versión 8.2.0
    id("com.android.application") version "8.13.2" apply false
    
    // Plugin de Kotlin para Android
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    
    // Safe Args para Navigation Component
    // Genera clases type-safe para los argumentos de navegación
    id("androidx.navigation.safeargs.kotlin") version "2.7.6" apply false
}

// Tarea para limpiar el directorio de build
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
