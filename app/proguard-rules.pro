# ============================================================================
# Tomorrowland Audio Player - ProGuard Rules
# ============================================================================
#
# ProGuard es una herramienta que:
# 1. Reduce el tamaño del APK (shrinking)
# 2. Ofusca el código (obfuscation)
# 3. Optimiza el bytecode
#
# Por ahora mantenemos las reglas mínimas para facilitar el debugging.
# En producción, se pueden agregar más reglas de optimización.

# Mantener atributos para stack traces legibles
-keepattributes SourceFile,LineNumberTable

# Mantener nombres de clases en excepciones
-renamesourcefileattribute SourceFile

# Reglas para Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Reglas para Navigation Safe Args
-keepnames class * extends android.os.Parcelable
-keepnames class * extends java.io.Serializable
