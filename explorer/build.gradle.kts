plugins {
    alias(libs.plugins.cuju.android.library)
}

dependencies {
    implementation(project(":core"))
    implementation(project(":ui"))
}
