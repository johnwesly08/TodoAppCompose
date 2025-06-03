// C:\Users\johnw\StudioProjects\TodoAppCompose\build.gradle.kts

buildscript {
    repositories {
        google() // This is for plugins used in this buildscript block itself
        mavenCentral()
    }
    dependencies {
        // Use a recent stable version of the Android Gradle Plugin
        classpath("com.android.tools.build:gradle:8.3.1")
        // Use a recent stable version of the Kotlin Gradle Plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23")
    }
}

plugins {
    // Apply plugins here, but set `apply false` as they are applied to sub-modules
    id("com.android.application") version "8.3.1" apply false
    id("com.android.library") version "8.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
}

// REMOVE THE FOLLOWING BLOCK COMPLETELY:
/*
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
*/