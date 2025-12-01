plugins {
    alias(libs.plugins.cuju.android.room.library)
    alias(libs.plugins.mappie)
}
cujuLokalise {
    projectId = "61539175692cfee0eff7f4.38675486"
}
dependencies {
    implementation(project(":room"))
    implementation(project(":core"))
    implementation(project(":ui"))
    implementation(libs.mappie.api)
}
