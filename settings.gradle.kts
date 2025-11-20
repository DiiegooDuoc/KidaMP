// Archivo: settings.gradle.kts (en la raíz de tu proyecto)

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // <-- AÑADE ESTA LÍNEA
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KidaMP"
include(":app")
