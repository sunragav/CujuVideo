package ext

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureWorkManager(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            add("implementation", libs.findLibrary("androidx-work-manager").get())
            add(
                "androidTestImplementation",
                libs.findLibrary("androidx-work-manager-testing").get()
            )
        }
    }
}
