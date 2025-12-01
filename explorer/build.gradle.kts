plugins {
    alias(libs.plugins.cuju.android.library)
}
cujuLokalise {
    projectId = "99347551692c22cbb183e4.36823657"
}
dependencies {
    implementation(project(":core"))
    implementation(project(":ui"))
    implementation(project(":videoSdk"))
}
