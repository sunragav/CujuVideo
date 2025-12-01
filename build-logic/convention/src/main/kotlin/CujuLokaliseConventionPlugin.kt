import ext.getPropertyFromLocalProperties
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.process.ExecOperations
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

open class CujuLokaliseExtension {
    var projectId: String? = null
}

// this plugin requires lokalise cli to be installed: https://github.com/lokalise/lokalise-cli-2-go
class CujuLokaliseConventionPlugin @Inject constructor(
    private val execOperations: ExecOperations
) : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("cujuLokalise", CujuLokaliseExtension::class.java)
        project.tasks.register("downloadStrings") {
            group = "Localization"
            description = "Downloads translations from Lokalise"

            doLast {
                val lokaliseProjectId = extension.projectId
                val lokaliseToken = project.getPropertyFromLocalProperties("lokalise.token")

                if (lokaliseProjectId.isNullOrBlank()) {
                    throw GradleException("cujuLokalise.projectId must be set")
                }
                if (lokaliseToken == null) {
                    throw GradleException("lokalise.token not found in local.properties")
                }

                val resPath = "${project.projectDir}/src/commonMain/composeResources/"

                val processText = ByteArrayOutputStream().use { outputStream ->
                    val cmdArgs = listOf(
                        "lokalise2",
                        "--token", lokaliseToken,
                        "--project-id", lokaliseProjectId,
                        "file", "download",
                        "--replace-breaks=true",
                        "--include-comments=false",
                        "--include-description=false",
                        "--indentation", "4sp",
                        "--format", "xml",
                        "--export-sort", "first_added",
                        "--unzip-to", resPath
                    )

                    execOperations.exec {
                        commandLine = cmdArgs
                        standardOutput = outputStream
                    }
                    outputStream.toString()
                }

                println(processText)

                unescapeLokaliseStrings(resPath)
            }
        }
    }

    // KMP does not support (and require) escaping string values
    // https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-resources-usage.html#strings
    private fun unescapeLokaliseStrings(dirPath: String) {
        val directory = File(dirPath)

        if (!directory.exists() || !directory.isDirectory) {
            throw GradleException("Directory '$dirPath' does not exist or is not a directory.")
        }

        directory.walkTopDown()
            .filter {
                it.isFile && it.extension == "xml" && it.parentFile.name.startsWith("values")
            }
            .forEach { file ->
                val original = file.readText(Charsets.UTF_8)
                val cleaned = original
                    //characters that required escaping https://kotlinlang.org/docs/characters.html
                    .replace("\\t", "\t")
                    .replace("\\b", "\b")
                    .replace("\\n", "\n")
                    .replace("\\r", "\r")
                    .replace("\\'", "'")
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\") // unescape backslashes
                    .replace("\\$", "$")

                if (original != cleaned) {
                    file.writeText(cleaned, Charsets.UTF_8)
                    println("Unescaped: ${file.path}")
                }
            }
    }
}
