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

package net.cacheoverflow.kotlinconventions.settings

import org.gradle.api.Plugin
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.initialization.Settings

@Suppress("Unused")
class ConventionsSettingsPlugin : Plugin<Settings> {
    override fun apply(target: Settings) = with(target) {
        configureDependencyResolutionManagement()
        configurePluginManagement()

        val parentDir = target.rootDir.parentFile
        fun includeCompositeBuild(name: String) {
            if (parentDir != null && parentDir.name == "notescure-monorepo") {
                val targetBuildDir = parentDir.resolve(name)
                if (targetBuildDir.exists()) {
                    includeBuild(targetBuildDir)
                }
            }
        }

        extensions.add("composites", ::includeCompositeBuild)
    }

    private fun Settings.configurePluginManagement() = pluginManagement {
        repositories {
            configureDefaultPluginRepositories()
        }
    }

    private fun Settings.configureDependencyResolutionManagement() = dependencyResolutionManagement {
        versionCatalogs {
            create("sharedLibs") {
                from("net.cacheoverflow.kotlinconventions:shared-catalog:1.0.0")
            }
        }
        @Suppress("UnstableApiUsage")
        repositories {
            configureDefaultProjectRepositories()
        }
    }

    private fun RepositoryHandler.configureDefaultProjectRepositories() {
        google {
            @Suppress("UnstableApiUsage")
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }

    private fun RepositoryHandler.configureDefaultPluginRepositories() {
        google {
            @Suppress("UnstableApiUsage")
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}
