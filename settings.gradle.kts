pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Public Dictionary"
include(":app")
include(":domain")
include(":data-repository")
include(":data-local")
include(":data-remote")
include(":presentation-common")
include(":presentation-dictionary")
