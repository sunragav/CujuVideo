plugins {
    alias(libs.plugins.cuju.android.application.compose)
}
cujuLokalise {
    projectId = "44968639691d443ab102a0.57382088"
}
dependencies {
    implementation(project(":camera"))
    implementation(project(":ui"))
    implementation(project(":videoplayer"))
    implementation(project(":explorer"))
}
