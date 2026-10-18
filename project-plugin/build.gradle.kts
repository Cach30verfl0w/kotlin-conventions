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

plugins {
    `kotlin-dsl`
    `maven-publish`
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
        register("KotlinConventions") {
            group = project.group
            id = group.toString()
            implementationClass = "$group.ConventionsPlugin"
        }
    }
}

fun Provider<PluginDependency>.asLibrary(): Provider<String> {
    return map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
}

dependencies {
    compileOnly(libs.plugins.kotlin.multiplatform.asLibrary())
    compileOnly(libs.plugins.android.application.asLibrary())
    compileOnly(libs.plugins.android.library.asLibrary())
    compileOnly(libs.plugins.mavenPublish.asLibrary())
    compileOnly(libs.plugins.detekt.asLibrary())
}
