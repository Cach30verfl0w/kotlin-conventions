allprojects {
    group = findProperty("project.group").toString()
    version = rootProject.libs.versions.kotlin.conventions.get()
}
