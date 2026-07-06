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

package net.cacheoverflow.conventions.extension

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import net.cacheoverflow.conventions.PluginIds
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

fun Project.defaultDetekt(version: String) = with(project.pluginManager) {
    withPlugin(PluginIds.DETEKT) {
        extensions.getByType<DetektExtension>().apply {
            toolVersion = version
            buildUponDefaultConfig = true
            config.setFrom(rootProject.layout.projectDirectory.dir("detekt.yml"))
            source.setFrom(files(layout.projectDirectory.dir("src")))
        }

        tasks.withType<Detekt>().configureEach {
            reports {
                html.required.set(true)
                md.required.set(true)
            }
        }
    }
}
