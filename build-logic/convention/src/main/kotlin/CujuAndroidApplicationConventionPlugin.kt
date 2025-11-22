import com.android.build.api.dsl.ApplicationExtension
import ext.buildTypeConfig
import ext.computeVersionCode
import ext.computeVersionName
import ext.configureKotlinAndroid
import ext.defaultBuildConfigFields
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class CujuAndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("com.google.devtools.ksp")
                apply("androidx.navigation.safeargs")
                apply("org.jetbrains.kotlin.plugin.parcelize")
                apply("cuju.android.lint")
                apply("cuju.kotlin.detekt")
            }
            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                compileSdk = libs.findVersion("compileSdkVersion").get().toString().toInt()
                defaultConfig {
                    applicationId = "com.cuju.video"
                    targetSdk = libs.findVersion("targetSdkVersion").get().toString().toInt()
                    minSdk = libs.findVersion("minSdkVersion").get().toString().toInt()
                    targetSdk = libs.findVersion("targetSdkVersion").get().toString().toInt()
                    versionCode = computeVersionCode()
                    versionName = computeVersionName()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    defaultBuildConfigFields()
                }
                buildTypeConfig(this)
                testOptions {
                    animationsDisabled = true
                    unitTests.isReturnDefaultValues = true
                    unitTests.all {
                        it.useJUnitPlatform()
                        it.maxHeapSize = "2048m"
                        // Uncomment the following to see detailed stacktrace of the failed tests
                        // it.testLogging.exceptionFormat = TestExceptionFormat.FULL
                    }
                }
                buildFeatures.buildConfig = true
            }
        }
    }
}
