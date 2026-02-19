// ============================================================================
// Tomorrowland Audio Player - Build Gradle del Módulo App
// Proyecto educativo para Ingeniería en Sistemas - Grupo H8B
// ============================================================================

plugins {
    // Plugin de Android para crear la aplicación
    id("com.android.application")
    
    // Plugin de Kotlin para Android
    id("org.jetbrains.kotlin.android")
    
    // Safe Args para Navigation Component
    // ⭐ Genera clases type-safe para pasar argumentos entre fragments
    id("androidx.navigation.safeargs.kotlin")
}

android {
    // Namespace del paquete de la aplicación
    namespace = "com.tomorrowland.audioplayer"
    
    // Versión del SDK de compilación (Android 14)
    compileSdk = 34

    defaultConfig {
        // Identificador único de la aplicación
        applicationId = "com.tomorrowland.audioplayer"
        
        // SDK mínimo requerido (Android 8.0 Oreo)
        minSdk = 26
        
        // SDK objetivo (Android 14)
        targetSdk = 34
        
        // Código de versión (incrementar con cada actualización)
        versionCode = 1
        
        // Nombre de versión visible para usuarios
        versionName = "1.0.0"

        // Runner para tests de instrumentación
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        // Configuración para release (producción)
        release {
            // Deshabilitar minificación para depuración más fácil
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    // Configuración de compatibilidad de Java
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    // Configuración de Kotlin
    kotlinOptions {
        jvmTarget = "17"
    }
    
    // ⭐ IMPORTANTE: Habilitar ViewBinding para acceder a vistas de forma segura
    buildFeatures {
        viewBinding = true
    }
}

// ============================================================================
// DEPENDENCIAS DEL PROYECTO
// ============================================================================
dependencies {
    // === Librerías Core de Android ===
    // Core KTX: Extensiones de Kotlin para Android
    implementation("androidx.core:core-ktx:1.12.0")
    
    // AppCompat: Compatibilidad hacia atrás para componentes UI
    implementation("androidx.appcompat:appcompat:1.6.1")
    
    // === Material Design 3 ===
    // Material Components: Librería principal de Material Design 3
    // ⭐ Versión 1.11.0+ para Material You y componentes modernos
    implementation("com.google.android.material:material:1.11.0")
    
    // === ConstraintLayout ===
    // Layout flexible y potente para interfaces complejas
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    
    // === Navigation Component ===
    // Fragment KTX para mejor manejo de fragments
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    
    // Navigation Fragment: Navegación entre fragments
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    
    // Navigation UI: Integración con componentes de UI (BottomNav, Toolbar)
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    
    // === Lifecycle y ViewModel ===
    // ViewModel: Gestión de datos que sobrevive a cambios de configuración
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    
    // LiveData: Datos observables que respetan el ciclo de vida
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    
    // Runtime KTX para lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    
    // === Glide para carga de imágenes ===
    // Glide: Librería eficiente para cargar y cachear imágenes
    implementation("com.github.bumptech.glide:glide:4.16.0")
    
    // === RecyclerView ===
    // RecyclerView: Lista eficiente y reciclable
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    
    // === CardView ===
    // CardView: Tarjetas con sombra y esquinas redondeadas
    implementation("androidx.cardview:cardview:1.0.0")
    
    // === ExoPlayer para reproducción de audio ===
    // Media3 ExoPlayer: Reproductor multimedia moderno de Google
    implementation("androidx.media3:media3-exoplayer:1.2.0")
    implementation("androidx.media3:media3-ui:1.2.0")
    implementation("androidx.media3:media3-session:1.2.0")
    
    // === Testing (Pruebas) ===
    // JUnit para pruebas unitarias
    testImplementation("junit:junit:4.13.2")
    
    // AndroidX Test para pruebas de instrumentación
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
