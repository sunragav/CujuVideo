package ext

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureRoom(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            add("implementation", libs.findLibrary("ksp-symbol-processing").get())
            add("api", libs.findBundle("room-api").get())
            add(
                "androidTestImplementation",
                libs.findLibrary("androidx-room-testing").get()
            )
            add(
                "testImplementation",
                libs.findLibrary("androidx-room-testing").get()
            )
            add("ksp", libs.findLibrary("room-compiler").get())
        }
    }
}
