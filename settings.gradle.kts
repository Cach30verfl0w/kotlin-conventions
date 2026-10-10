rootProject.name = "kotlin-conventions"
include("settings-plugin", "shared-catalog", "project-plugin")

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}
