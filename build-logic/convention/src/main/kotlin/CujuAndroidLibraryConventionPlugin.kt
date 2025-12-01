@file:Suppress("UnstableApiUsage")

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import ext.configureCoroutines
import ext.configureKoin
import ext.configureKotlinAndroid
import ext.configureWorkManager
import ext.disableUnnecessaryAndroidTests
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class CujuAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("cuju.android.lint")
                apply("cuju.kotlin.detekt")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("cuju.lokalise")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureAndroidCompose(this)
                configureCoroutines(this)
                configureKoin(this)
                configureWorkManager(this)
                defaultConfig.targetSdk =
                    libs.findVersion("targetSdkVersion").get().toString().toInt()
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.animationsDisabled = true
                testOptions {
                    unitTests.all {
                        it.useJUnitPlatform()
                        it.maxHeapSize = "2048m"
                        it.testLogging.exceptionFormat = TestExceptionFormat.FULL
                    }
                }
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {
                add("androidTestImplementation", kotlin("test"))
                add("testImplementation", kotlin("test"))
            }
        }
    }
}
