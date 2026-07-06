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

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
fun KotlinNativeTarget.defaultMacOSTarget() = Unit

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
inline fun KotlinMultiplatformExtension.withMacOS(crossinline config: KotlinNativeTarget.() -> Unit = {}) {
    macosArm64 {
        defaultMacOSTarget()
        config()
    }
}

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
fun KotlinNativeTarget.defaultIosTarget() = Unit

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
inline fun KotlinMultiplatformExtension.withIos(crossinline config: KotlinNativeTarget.() -> Unit = {}) {
    iosArm64 {
        defaultIosTarget()
        config()
    }
    iosSimulatorArm64 {
        defaultIosTarget()
        config()
    }
}

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
fun KotlinNativeTarget.defaultLinuxTarget() = Unit

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
inline fun KotlinMultiplatformExtension.withLinux(crossinline config: KotlinNativeTarget.() -> Unit = {}) {
    linuxX64 {
        defaultLinuxTarget()
        config()
    }
    linuxArm64 {
        defaultLinuxTarget()
        config()
    }
}

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
fun KotlinNativeTarget.defaultMingwTarget() = Unit

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
inline fun KotlinMultiplatformExtension.withMingw(crossinline config: KotlinNativeTarget.() -> Unit = {}) {
    mingwX64 {
        defaultMingwTarget()
        config()
    }
}

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
inline fun KotlinMultiplatformExtension.withNative(crossinline config: KotlinNativeTarget.() -> Unit = {}) {
    withLinux(config)
    withMacOS(config)
    withMingw(config)
    withIos(config)
}

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
inline fun KotlinMultiplatformExtension.withJvm(crossinline config: KotlinJvmTarget.() -> Unit = {}) {
    jvm { config() }
}

/**
 * @author Cedric Hammes
 * @since  06/07/2026
 */
fun KotlinMultiplatformExtension.defaultCompilerOptions() {
    compilerOptions {
        freeCompilerArgs.addAll("-Xexpect-actual-classes", "-Xcontext-parameters")
    }
}
