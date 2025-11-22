package ext

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

@Suppress("EnumEntryName")
enum class CujuBuildType {
    debug,
    release
}

internal fun Project.buildTypeConfig(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildTypes {
            getByName(CujuBuildType.debug.name) {
            }
            getByName(CujuBuildType.release.name) {
                isMinifyEnabled = true
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
                val proguardFiles = fileTree("proguard") {
                    include("*.pro")
                }
                proguardFiles(*proguardFiles.toList().toTypedArray())
            }
        }
    }
}
