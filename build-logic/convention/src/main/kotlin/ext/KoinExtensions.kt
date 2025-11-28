package ext

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKoin(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            add("implementation", libs.findLibrary("koin-android").get())
            add("implementation", libs.findLibrary("koin-androidx-compose").get())
            add("implementation", libs.findLibrary("koin-core").get())
            add("implementation", libs.findLibrary("koin-compose").get())
            add("implementation", libs.findLibrary("koin-compose-viewmodel").get())
        }
    }
}
