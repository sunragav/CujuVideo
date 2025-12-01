plugins {
    alias(libs.plugins.cuju.android.video.player.library)
}
cujuLokalise {
    projectId = "32695501692cfe780091b6.71738365"
}
dependencies {
    implementation(project(":core"))
    implementation(project(":ui"))
    implementation(project(":videoSdk"))
}
