package ext

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCamera(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("implementation", libs.findLibrary("androidx-camera-core").get())
            add("implementation", libs.findLibrary("androidx-camera-lifecycle").get())
            add("implementation", libs.findLibrary("androidx-camera-view").get())
            add("implementation", libs.findLibrary("androidx-camera-camera2").get())
            add("implementation", libs.findLibrary("androidx-camera-video").get())
            add("implementation", libs.findLibrary("androidx-camera-extensions").get())
        }
    }
}

internal fun Project.configurePermissions(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            add("implementation", libs.findLibrary("accompanist-permissions").get())
        }
    }
}
