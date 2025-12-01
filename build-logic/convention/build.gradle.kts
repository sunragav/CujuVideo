import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.cuju.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}
tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}
gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "cuju.android.application.compose"
            implementationClass = "CujuAndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "cuju.android.library"
            implementationClass = "CujuAndroidLibraryConventionPlugin"
        }
        register("androidCameraLibrary") {
            id = "cuju.android.camera.library"
            implementationClass = "CujuAndroidCameraLibraryConventionPlugin"
        }
        register("androidVideoPlayerLibrary") {
            id = "cuju.android.video.player"
            implementationClass = "CujuAndroidVideoPlayerConventionPlugin"
        }
        register("androidRoom") {
            id = "cuju.android.room"
            implementationClass = "CujuAndroidRoomDatabaseConventionPlugin"
        }
        register("lokalise") {
            id = "cuju.lokalise"
            implementationClass = "CujuLokaliseConventionPlugin"
        }
        register("androidLint") {
            id = "cuju.android.lint"
            implementationClass = "common.AndroidLintConventionPlugin"
        }
        register("kotlinDetekt") {
            id = "cuju.kotlin.detekt"
            implementationClass = "common.DetektConventionPlugin"
        }
    }
}
