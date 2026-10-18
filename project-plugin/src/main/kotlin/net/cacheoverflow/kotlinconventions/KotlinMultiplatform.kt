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

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JsSourceMapEmbedMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBrowserDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsSubTargetDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

@PublishedApi
internal val Project.kotlin: KotlinMultiplatformExtension get() = the()

/**
 * Configures a JVM target for the Kotlin Multiplatform project with a specified Java version.
 *
 * @param javaVersion the Java version, defaulting to the version in libs.versions.toml
 * @param config additional configuration block for the [KotlinJvmTarget]
 *
 * @author Cedric Hammes
 * @since  16/08/2026
 */
inline fun Project.withJvm(
    javaVersion: Provider<String> = getVersionProvider("jvmTarget"),
    crossinline config: KotlinJvmTarget.() -> Unit = {}
) = with(kotlin) {
    jvmToolchain(javaVersion.get().toInt())
    jvm {
        config()
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(javaVersion.get()))
        }
    }
}

fun KotlinNativeTarget.defaultIosTarget() {

}

/**
 * Configures standard iOS targets (ARM64 for devices and Simulator ARM64 for Apple Silicon Macs) for
 * the Kotlin Multiplatform project.
 *
 * @param config additional configuration block applied to each [KotlinNativeTarget]
 *
 * @author Cedric Hammes
 * @since  16/08/2026
 */
inline fun Project.withIos(crossinline config: KotlinNativeTarget.() -> Unit = {}) = with(kotlin) {
    iosArm64 {
        defaultIosTarget()
        config()
    }
    iosSimulatorArm64 {
        defaultIosTarget()
        config()
    }
}

fun KotlinNativeTarget.defaultMacOSTarget() {

}

/**
 * Configures standard macOS targets for the Kotlin Multiplatform project.
 *
 * @param config additional configuration block applied to each [KotlinNativeTarget]
 *
 * @author Cedric Hammes
 * @since  18/08/2026
 */
inline fun Project.withMacOS(crossinline config: KotlinNativeTarget.() -> Unit = {}) = with(kotlin) {
    macosArm64 {
        defaultMacOSTarget()
        config()
    }
}

fun KotlinNativeTarget.defaultMingwTarget() {

}

/**
 * Configures standard Apple-based targets for the Kotlin Multiplatform project.
 *
 * @param config additional configuration block applied to each [KotlinNativeTarget]
 *
 * @author Cedric Hammes
 * @since  18/08/2026
 */
inline fun Project.withApple(crossinline config: KotlinNativeTarget.() -> Unit = {}) {
    withIos(config)
    withMacOS(config)
}

/**
 * Configures standard Windows-compatible targets for the Kotlin Multiplatform project.
 *
 * @param config additional configuration block applied to each [KotlinNativeTarget]
 *
 * @author Cedric Hammes
 * @since  18/08/2026
 */
inline fun Project.withMingw(crossinline config: KotlinNativeTarget.() -> Unit = {}) = with(kotlin) {
    mingwX64 {
        defaultMingwTarget()
        config()
    }
}

fun KotlinNativeTarget.defaultLinuxTarget() {

}

/**
 * Configures standard Linux targets for the Kotlin Multiplatform project.
 *
 * @param config additional configuration block applied to each [KotlinNativeTarget]
 *
 * @author Cedric Hammes
 * @since  18/08/2026
 */
inline fun Project.withLinux(crossinline config: KotlinNativeTarget.() -> Unit = {}) = with(kotlin) {
    linuxX64 {
        defaultLinuxTarget()
        config()
    }
    linuxArm64 {
        defaultLinuxTarget()
        config()
    }
}

inline fun Project.withNative(crossinline config: KotlinNativeTarget.() -> Unit = {}) {
    withApple(config)
    withLinux(config)
    withMingw(config)
}

fun KotlinJsTargetDsl.defaultJsTarget() {
    compilerOptions {
        sourceMap.set(true)
        sourceMapEmbedSources.set(JsSourceMapEmbedMode.SOURCE_MAP_SOURCE_CONTENT_ALWAYS)
    }
}

fun KotlinJsSubTargetDsl.defaultBrowserConfig() {
    testTask {
        useKarma {
            useFirefoxHeadless()
        }
    }
}

inline fun KotlinJsTargetDsl.withBrowser(crossinline config: KotlinJsBrowserDsl.() -> Unit = {}) {
    browser {
        defaultBrowserConfig()
        config()
    }
}

/**
 * Configures the JavaScript target for the Kotlin Multiplatform project.
 *
 * @param config The configuration to apply to the JavaScript target
 *
 * @author Cedric Hammes
 * @since  18/08/2026
 */
inline fun Project.withJs(crossinline config: KotlinJsTargetDsl.() -> Unit = {}) = with(kotlin) {
    js {
        defaultJsTarget()
        config()
    }
}

/**
 * Configures the WebAssembly (Wasm) target for the Kotlin Multiplatform project.
 *
 * @param config The configuration to apply to the Wasm target
 *
 * @author Cedric Hammes
 * @since  18/08/2026
 */
@OptIn(ExperimentalWasmDsl::class)
inline fun Project.withWasm(crossinline config: KotlinJsTargetDsl.() -> Unit = {}) = with(kotlin) {
    wasmJs { config() }
}

inline fun Project.withWeb(crossinline config: KotlinJsTargetDsl.() -> Unit = {}) {
    withJs(config)
    withWasm(config)
}

/**
 * Configures an Android library target for the Kotlin Multiplatform project with specified namespace
 * and SDK versions.
 *
 * @param namespace the Android namespace for the library, defaulting to the project's group
 * @param compileSdk the compile SDK version, defaulting to the compile Sdk version in libs.versions.toml
 * @param minSdk the minimum SDK version, defaulting to the min Sdk version in libs.versions.toml
 * @param javaVersion the JVM target version, defaulting to the JVM target in libs.versions.toml
 * @param config additional configuration block for the [KotlinAndroidTarget]
 *
 * @author Cedric Hammes
 * @since  16/08/2026
 */
inline fun Project.withAndroidLibrary(
    namespace: String = project.group.toString(),
    compileSdk: Provider<Int> = getVersionProvider("android.compileSdk").map { it.toInt() },
    minSdk: Provider<Int> = getVersionProvider("android.minSdk").map { it.toInt() },
    javaVersion: Provider<String> = getVersionProvider("jvmTarget"),
    crossinline config: KotlinMultiplatformAndroidLibraryTarget.() -> Unit = {}
) = with(kotlin) {
    extensions.configure<KotlinMultiplatformAndroidLibraryTarget>("androidLibrary") {
        this.namespace = namespace
        this.compileSdk = compileSdk.get()
        this.minSdk = minSdk.get()
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(javaVersion.get()))
        }
        config()

        androidResources.enable = true
    }
}
