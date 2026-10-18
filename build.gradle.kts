import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.authentication.http.HttpHeaderAuthentication

val baseVersion = rootProject.libs.versions.kotlin.conventions.get()
val ciJobId = providers.environmentVariable("CI_PIPELINE_ID").orNull
val ciCommitTag = providers.environmentVariable("CI_COMMIT_TAG").orNull
val projectVersion = when {
    ciCommitTag != null -> baseVersion
    ciJobId != null -> "$baseVersion-SNAPSHOT+$ciJobId"
    else -> baseVersion
}

allprojects {
    group = findProperty("project.group").toString()
    version = projectVersion

    pluginManager.withPlugin("maven-publish") {
        val gitLabApiUrl = providers.environmentVariable("CI_API_V4_URL").orNull
        val gitLabProjectId = providers.environmentVariable("CI_PROJECT_ID").orNull
        val gitLabJobToken = providers.environmentVariable("CI_JOB_TOKEN").orNull

        if (gitLabApiUrl != null && gitLabProjectId != null && gitLabJobToken != null) {
            extensions.configure<PublishingExtension> {
                repositories {
                    maven {
                        name = "GitLab"
                        url = uri("$gitLabApiUrl/projects/$gitLabProjectId/packages/maven")
                        credentials(HttpHeaderCredentials::class) {
                            name = "Job-Token"
                            value = gitLabJobToken
                        }
                        authentication {
                            create("header", HttpHeaderAuthentication::class)
                        }
                    }
                }
            }
        }
    }
}
