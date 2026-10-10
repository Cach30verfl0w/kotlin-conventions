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

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import net.cacheoverflow.kotlinconventions.getPluginId
import net.cacheoverflow.kotlinconventions.getVersionProvider
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

class ConventionsPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins.withId(getPluginId("detekt").get()) {
            applyDetekt()
        }
    }

    private fun Project.applyDetekt() {
        extensions.configure<DetektExtension>("detekt") {
            buildUponDefaultConfig = true
            toolVersion = getVersionProvider("detekt").get()
            config.setFrom(rootProject.layout.projectDirectory.file("detekt.yml"))
            source.setFrom(files(layout.projectDirectory.dir("src")))
        }

        tasks.withType<Detekt>().configureEach {
            reports {
                html.required.set(true)
                md.required.set(false)
            }
        }
    }
}
