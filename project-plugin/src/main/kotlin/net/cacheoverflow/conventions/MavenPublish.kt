/*
 * Copyright 2026 Cedric Hammes <contact@cach30verfl0w.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cacheoverflow.conventions

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.kotlin.dsl.configure

fun MavenPom.apache2License() {
    licenses {
        license {
            name.set("Apache License, version 2.0")
            url.set("https://www.apache.org/licenses/LICENSE-2.0")
        }
    }
}

fun MavenPom.projectMetadata(name: String, description: String, repository: String, server: String = "github.com") {
    this.name.set(name)
    this.description.set(description)
    url.set("https://$server/$repository")
    scm {
        url.set("https://$server/$repository")
        connection.set("scm:git:https://$server/$repository.git")
        developerConnection.set("scm:git:ssh://git@$server:$repository.git")
    }
}

fun MavenPom.defaultDevelopers() {
    developers {
        developer {
            id.set("cach30verfl0w")
            name.set("Cedric Hammes")
            url.set("https://cach30verfl0w.net")
            timezone.set("Europe/Berlin")
            email.set("contact@cach30verfl0w.net")
        }
    }
}

fun Project.defaultPublishing(closure: PublishingExtension.() -> Unit = {}) = with(pluginManager) {
    withPlugin(PluginIds.MAVEN_PUBLISH) {
        extensions.configure<PublishingExtension> {
            closure()
        }
    }

    withPlugin(PluginIds.VANNIKTECH_MAVEN_PUBLISH) {
        extensions.configure<MavenPublishBaseExtension> {
            publishToMavenCentral()
            signAllPublications()
        }
    }
}