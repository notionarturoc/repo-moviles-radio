// ============================================================================
// Tomorrowland Audio Player - Configuración de Settings Gradle
// Proyecto educativo para Ingeniería en Sistemas
// ============================================================================

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// Repositorios de dependencias
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// Nombre del proyecto raíz
rootProject.name = "TomorrowlandAudioPlayer"

// Módulos del proyecto (solo el módulo app)
include(":app")
