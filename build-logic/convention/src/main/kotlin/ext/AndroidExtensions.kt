@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.CommonExtension
import ext.libs
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        namespace = "com.cuju.video"
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
            add("implementation", libs.findLibrary("androidx-activity-compose").get())
            add("implementation", libs.findLibrary("androidx-compose-ui").get())
            add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
            add("implementation", libs.findLibrary("androidx-compose-ui-util").get())
            add("implementation", libs.findLibrary("androidx-compose-material3").get())
            add("implementation", libs.findLibrary("androidx-lifecycle-runtime-compose").get())
            add("implementation", libs.findLibrary("androidx-navigation-compose").get())
            add("implementation", libs.findLibrary("androidx-paging-compose").get())
            add("implementation", libs.findLibrary("androidx-compose-ui-viewbinding").get())
            add("implementation", libs.findLibrary("androidx-adaptive-android").get())
            add("implementation", libs.findLibrary("compose-material-icons").get())
            add("implementation", libs.findLibrary("fastscroller-core").get())
            add("implementation", libs.findLibrary("fastscroller-indicator").get())
            add("implementation", libs.findLibrary("fastscroller-material").get())
            add(
                "androidTestImplementation",
                libs.findLibrary("androidx-compose-ui-test-junit4").get()
            )
            add(
                "androidTestImplementation",
                libs.findLibrary("androidx-compose-ui-test-manifest").get()
            )
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
        }
    }
    extensions.configure<ComposeCompilerGradlePluginExtension> {
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }
        fun Provider<*>.relativeToRootProject(dir: String) = flatMap {
            rootProject.layout.buildDirectory.dir(projectDir.toRelativeString(rootDir))
        }.map { it.dir(dir) }

        project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
            .relativeToRootProject("compose-metrics")
            .let(metricsDestination::set)

        project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
            .relativeToRootProject("compose-reports")
            .let(reportsDestination::set)

        stabilityConfigurationFiles =
            listOf(rootProject.layout.projectDirectory.file("stability_config.conf"))
    }
}
