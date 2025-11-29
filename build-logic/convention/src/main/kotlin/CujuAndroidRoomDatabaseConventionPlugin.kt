import androidx.room.gradle.RoomExtension
import com.android.build.gradle.LibraryExtension
import com.google.devtools.ksp.gradle.KspExtension
import ext.configureRoom
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class CujuAndroidRoomDatabaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("cuju.android.library")
                apply("androidx.room")
                apply("com.google.devtools.ksp")
            }
            extensions.configure<KspExtension> {
                arg("room.generateKotlin", "true")
            }

            extensions.configure<RoomExtension> {
                // The schemas directory contains a schema file for each version of the Room database.
                // This is required to enable Room auto migrations.
                // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
                schemaDirectory("$projectDir/schemas")
            }
            extensions.configure<LibraryExtension> {
                configureRoom(this)
            }
        }
    }
}
