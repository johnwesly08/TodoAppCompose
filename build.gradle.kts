// C:\Users\johnw\StudioProjects\TodoAppCompose\build.gradle.kts

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // --- THIS IS THE CRITICAL LINE TO CHECK ---
        // The Android Gradle Plugin (AGP) classpath dependency MUST be a direct string.
        // Use the 'agp' version from your libs.versions.toml for consistency.
        classpath("com.android.tools.build:gradle:${libs.versions.agp.get()}") // Correct way to use version catalog here

        // The Kotlin Gradle Plugin classpath dependency should also be a direct string.
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    }
}

plugins {
    // These lines are correct for applying plugins via version catalogs
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // If you removed kotlin-compose from libs.versions.toml, remove this line:
    // alias(libs.plugins.kotlin.compose) apply false
}