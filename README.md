# Kotlin Conventions

Kotlin Conventions provides Gradle plugins, reusable Kotlin DSL helpers, and a shared version
catalog for Kotlin and Kotlin Multiplatform projects.

## Features

- Configures standard repositories for Gradle plugins and project dependencies.
- Provides a shared `sharedLibs` version catalog for Kotlin, Android, Compose Multiplatform, Ktor,
  and other common dependencies.
- Supplies helpers for configuring JVM, Android, iOS, macOS, Linux, Windows, JavaScript, and
  WebAssembly targets in Kotlin Multiplatform projects.
- Configures Android applications and Kotlin Multiplatform Android libraries with version-catalog
  defaults.
- Provides Maven Central publishing helpers for project coordinates, POM metadata, licenses, and GPG
  signing.
- Supports CI-aware snapshot versions and publishing to the GitLab Maven package registry.

## Modules

| Module            | Purpose                                                                               |
|-------------------|---------------------------------------------------------------------------------------|
| `settings-plugin` | Configures plugin and dependency repositories and exposes the shared version catalog. |
| `project-plugin`  | Provides the project convention plugin and reusable Kotlin DSL helpers.               |
| `shared-catalog`  | Publishes the shared Gradle version catalog.                                          |

## License

This project is licensed under the [Apache License 2.0](LICENSE).

```
Copyright 2026 Cedric Hammes <contact@cach30verfl0w.net>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
    
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```