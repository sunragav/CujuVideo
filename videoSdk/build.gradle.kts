plugins {
    alias(libs.plugins.cuju.android.library)
}

dependencies {
    implementation(project(":room"))
    implementation(project(":core"))
}
