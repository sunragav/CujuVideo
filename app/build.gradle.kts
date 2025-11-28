plugins {
    alias(libs.plugins.cuju.android.application.compose)
}

dependencies {
    implementation(project(":ui"))
    implementation(project(":videoplayer"))
    implementation(project(":explorer"))
}
