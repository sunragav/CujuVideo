plugins {
    alias(libs.plugins.cuju.android.application.compose)
}

android {
    namespace = "com.cuju.video"
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
