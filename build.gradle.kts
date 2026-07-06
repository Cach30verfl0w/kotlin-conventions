import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin

plugins {
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.mavenPublish).apply(false)
    `maven-publish`
}

val projectGroup = requireNotNull(property("project.group") as? String) { "Missing project.group property" }
val projectVersion = libs.versions.conventions.get()
val isCiPipeline = System.getenv("CI") != null

fun getBuildNumber(): String = System.getProperty("CI_PIPELINE_IID")
    ?: System.getProperty("GITHUB_RUN_NUMBER")
    ?: "0"

fun isSnapshot(): Boolean {
    val refName = System.getenv("CI_COMMIT_REF_NAME") ?: System.getProperty("GITHUB_REF_NAME") ?: "local"
    return when(refName) {
        "main", "master" -> false
        else -> true
    }
}

allprojects {
    group = requireNotNull(property("project.group") as? String) { "Missing project.group property" }
    version = "$projectVersion.${getBuildNumber()}${if (isSnapshot()) "-SNAPSHOT" else ""}"

    pluginManager.withPlugin("com.vanniktech.maven.publish") {
        extensions.configure<MavenPublishBaseExtension> {
            publishToMavenCentral()
            signAllPublications()
        }

        extensions.configure<SigningExtension> {
            isRequired = isCiPipeline
        }
    }

    apply<MavenPublishPlugin>()
    apply<SigningPlugin>()
    publishing {
        repositories {
            publications.withType<MavenPublication> {
                pom {
                    name = project.name
                    description = "Just a conventions plugin for Gradle used in some Kotlin Multiplatform projects."
                    url = "https://github.com/cach30verfl0w/kotlin-conventions"
                    licenses {
                        license {
                            name = "Apache License 2.0"
                            url = "https://www.apache.org/licenses/LICENSE-2.0"
                        }
                    }
                    developers {
                        developer {
                            id = "cach30verfl0w"
                            name = "Cedric Hammes"
                            url = "https://cach30verfl0w.net"
                        }
                    }
                    scm {
                        connection = "scm:git:https://github.com/cach30verfl0w/kotlin-conventions"
                        developerConnection = "scm:git:ssh://git@github.com:cach30verfl0w/kotlin-conventions"
                        url = "https://github.com/cach30verfl0w/kotlin-conventions"
                    }
                }
            }
        }
    }
}