package ext

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import java.util.Properties

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
fun Project.getPropertyFromLocalProperties(key: String): String? {
    val localPropertiesFile = rootProject.file("local.properties")

    return if (localPropertiesFile.exists()) {
        Properties().let {
            it.load(localPropertiesFile.inputStream())
            it.getProperty(key)
        }
    } else {
        null
    }
}
