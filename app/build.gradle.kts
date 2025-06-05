// app/build.gradle.kts
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.todoappcompose" // Make sure this matches your actual package
    compileSdk = 35 // Or higher, ensure consistency

    defaultConfig {
        applicationId = "com.example.todoappcompose"
        minSdk = 24
        targetSdk = 35 // Or higher
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Typo corrected: was `isMinyfyEnabled`
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        // IMPORTANT: Ensure this is true for Compose
        compose = true
    }
    composeOptions {
        // IMPORTANT: This version must be compatible with your compose-bom version
        // Android Studio usually suggests the correct one.
        kotlinCompilerExtensionVersion = "1.5.12" // Or higher, e.g., "1.5.11" for latest Kotlin
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Android Core KTX
    implementation(libs.androidx.core.ktx.v1160)
    implementation(libs.androidx.lifecycle.runtime.ktx.v280)
    implementation(libs.androidx.activity.compose.v1101)

    // IMPORTANT: Compose BOM (Bill of Materials) - ensures all Compose libs are compatible
    // Use the latest stable version from https://developer.android.com/jetpack/androidx/releases/compose-bom
    implementation(platform(libs.androidx.compose.bom.v20250600)) // Current example version

    // Core Compose UI, Graphics, Tooling
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Material 3 components
    implementation(libs.androidx.material3)

    // IMPORTANT: ViewModel integration for Compose
    // This is the dependency that provides `viewModel()`
    implementation(libs.androidx.lifecycle.viewmodel.compose) // Match lifecycle-runtime-ktx version

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v121)
    androidTestImplementation(libs.androidx.espresso.core.v361)
    androidTestImplementation(platform(libs.androidx.compose.bom.v20250600)) // For UI testing
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}