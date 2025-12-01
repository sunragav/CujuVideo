package ext

import org.gradle.api.Project

const val BUILD_NUMBER_RANGE = 1000 // [0..999]
const val PATCH_MULTIPLIER = 1000
const val PATCH_RANGE = 100 // [0Xxx..99Xxx]
const val MINOR_MULTIPLIER = 100000
const val MINOR_RANGE = 100 // [0xxXxx..99xxXxx]
const val MAJOR_MULTIPLIER = 10000000

// https://developer.android.com/studio/publish/versioning
// "Warning: The greatest value Google Play allows for versionCode is 2100000000."
const val MAJOR_RANGE = 200 // [0xXxxXxx..199xXxxXxx]

private val Project.versionMinor get() = libs.findVersion("versionMinor").get().requiredVersion
private val Project.versionMajor get() = libs.findVersion("versionMajor").get().requiredVersion
private val Project.versionPatch get() = libs.findVersion("versionPatch").get().requiredVersion

fun Project.computeBuildNumber(): Int {
    val buildNumber = System.getenv("BUILD_ID")
    return if (buildNumber != null) {
        Integer.valueOf(buildNumber) % BUILD_NUMBER_RANGE
    } else if (project.hasProperty("versionCodeOverride")) {
        Integer.valueOf(project.properties["versionCodeOverride"].toString()) % BUILD_NUMBER_RANGE
    } else {
        0
    }
}

fun Project.computeVersionName() =
    "$versionMajor.$versionMinor.$versionPatch+${computeBuildNumber()}"

fun Project.computeVersionCode() =
    (validateRangeAndReturn(versionMajor.toInt(), MAJOR_RANGE) * MAJOR_MULTIPLIER) +
            (validateRangeAndReturn(versionMinor.toInt(), MINOR_RANGE) * MINOR_MULTIPLIER) +
            (validateRangeAndReturn(versionPatch.toInt(), PATCH_RANGE) * PATCH_MULTIPLIER) +
            (validateRangeAndReturn(computeBuildNumber(), BUILD_NUMBER_RANGE))

fun validateRangeAndReturn(number: Int, rangeExclusive: Int): Int {
    if (number !in 0 until rangeExclusive) {
        throw IllegalStateException("Invalid version")
    }
    return number
}
