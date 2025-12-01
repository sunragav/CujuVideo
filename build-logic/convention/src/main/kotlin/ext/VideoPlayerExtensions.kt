package ext

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureVideoPlayer(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            add("implementation", libs.findLibrary("media3-common").get())
            add("implementation", libs.findLibrary("media3-effect").get())
            add("implementation", libs.findLibrary("media3-exoplayer").get())
            add("implementation", libs.findLibrary("media3-transformer").get())
            add("implementation", libs.findLibrary("media3-ui").get())
            add("implementation", libs.findLibrary("media3-ui-compose").get())
        }
    }
}
