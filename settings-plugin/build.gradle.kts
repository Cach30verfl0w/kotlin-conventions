plugins {
    `kotlin-dsl`
}

val javaVersion: Int = libs.versions.jvmTarget.get().toInt()
java {
    withSourcesJar()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

kotlin {
    jvmToolchain(javaVersion)
}

gradlePlugin {
    plugins {
        register("KotlinConventionsSettings") {
            group = project.group
            id = "$group.${project.name}"
            implementationClass = "$group.settings.ConventionsSettingsPlugin"
        }
    }
}
