@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.LibraryExtension
import ext.configureVideoPlayer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class CujuAndroidVideoPlayerConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("cuju.android.library")
            }

            extensions.configure<LibraryExtension> {
                configureVideoPlayer(this)
            }
        }
    }
}
