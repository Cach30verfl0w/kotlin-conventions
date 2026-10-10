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

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.the

@PublishedApi
internal val Project.androidApplication: ApplicationExtension get() = the()

inline fun Project.withAndroidApplication(
    namespace: String = project.group.toString(),
    compileSdk: Provider<Int> = getVersionProvider("android.compileSdk").map { it.toInt() },
    targetSdk: Provider<Int> = getVersionProvider("android.targetSdk").map { it.toInt() },
    minSdk: Provider<Int> = getVersionProvider("android.minSdk").map { it.toInt() },
    crossinline closure: ApplicationExtension.() -> Unit = {}
) = with(androidApplication) {
    this.namespace = namespace
    this.compileSdk = compileSdk.get()
    closure()
    defaultConfig {
        this.applicationId = namespace
        this.targetSdk = targetSdk.get()
        this.minSdk = minSdk.get()
    }
}
