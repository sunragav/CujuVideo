package ext

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project

internal fun Project.configureDetekt(extension: DetektExtension) {
    extension.apply {
        toolVersion = libs.findVersion("detektVersion").get().toString()
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        autoCorrect = false
    }
}
