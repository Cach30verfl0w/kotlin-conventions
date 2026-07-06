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

import net.cacheoverflow.conventions.PluginIds
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
fun Int.toJavaVersion(): JavaVersion {
    val javaVersion = JavaVersion.entries.find { it.majorVersion.toIntOrNull() == this@toJavaVersion }
    return requireNotNull(javaVersion) { "Invalid Java major version: $this" }
}

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
fun Project.configureJava(version: Int) = with(project.pluginManager) {
    withPlugin(PluginIds.JAVA) {
        logger.info("Found Java plugin, adjusting Java version")
        extensions.getByType<JavaPluginExtension>().apply {
            val javaVersion = version.toJavaVersion()
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(version))
            }
        }
    }
    withPlugin(PluginIds.KOTLIN_JVM) {
        logger.info("Found Kotlin JVM plugin, adjusting Java version")
        extensions.getByType<KotlinJvmExtension>().apply {
            jvmToolchain(version)
        }
    }
    withPlugin(PluginIds.KOTLIN_MULTIPLATFORM) {
        logger.info("Found Kotlin Multiplatform plugin, adjusting Java version")
        extensions.getByType<KotlinMultiplatformExtension>().apply {
            jvmToolchain(version)
        }
    }
}
