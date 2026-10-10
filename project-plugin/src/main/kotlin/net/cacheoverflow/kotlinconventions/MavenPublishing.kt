/*
 * Copyright 2026 Cedric Hammes <contact@cach30verfl0w.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cacheoverflow.kotlinconventions

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.kotlin.dsl.the
import org.gradle.plugins.signing.SigningExtension

@PublishedApi
internal val Project.mavenPublishing: MavenPublishBaseExtension get() = the()

@PublishedApi
internal val Project.mavenSigning: SigningExtension get() = the()

fun MavenPom.afferoGeneralPublicLicense() {
    licenses {
        license {
            name.set("GNU Affero General Public License v3.0 or later")
            url.set("https://www.gnu.org/licenses/agpl-3.0.html")
        }
    }
}

fun MavenPom.apacheLicense2() {
    licenses {
        license {
            name.set("Apache License 2.0")
            url.set("https://www.apache.org/licenses/LICENSE-2.0")
        }
    }
}

fun MavenPom.defaultDevelopers() {
    developers {
        developer {
            id.set("cach30verfl0w")
            name.set("Cedric Hammes")
            email.set("contact@cach30verfl0w.net")
            url.set("https://cach30verfl0w.net/")
        }
    }
}

fun Project.withSigning() = with(mavenSigning) {
    useGpgCmd()
    isRequired = true
    with(mavenPublishing) {
        signAllPublications()
    }
}

inline fun Project.withMavenPublish(crossinline closure: MavenPom.() -> Unit) = with(mavenPublishing) {
    publishToMavenCentral()
    coordinates(group.toString(), name, version.toString())
    pom {
        closure()
    }
}
