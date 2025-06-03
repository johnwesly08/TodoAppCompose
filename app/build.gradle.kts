// C:\Users\johnw\StudioProjects\TodoAppCompose\app\build.gradle.kts

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Note: You typically *don't* explicitly apply 'org.jetbrains.kotlin.plugin.compose' here.
    // The `compose = true` in the `android` block, along with the `kotlinCompilerExtensionVersion`,
    // tells the Android Gradle Plugin to handle the Compose compiler setup.
}

android {
    // Set your application's unique package name
    namespace = "com.johnw.todoappcompose"
    // Compile against the latest stable Android SDK
    compileSdk = 34 // Android 14

    defaultConfig {
        applicationId = "com.johnw.todoappcompose"
        minSdk = 24 // Minimum API level your app supports (e.g., Android 7.0)
        targetSdk = 34 // Target API level for your app
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Set to true for production releases
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

    // Enable Jetpack Compose features
    buildFeatures {
        compose = true
    }

    // Configure Compose compiler options
    composeOptions {
        // IMPORTANT: This version MUST match the Kotlin version used in your project-level build.gradle.kts
        // For Kotlin 1.9.23, the compatible Compose Compiler Extension version is 1.5.11 (as of late 2024 / early 2025).
        // ALWAYS check the official compatibility map: https://developer.android.com/jetpack/compose/bom/mapping
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core KTX provides extensions for Kotlin
    implementation("androidx.core:core-ktx:1.13.1")
    // Lifecycle components for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
    // Activity integration for Compose
    implementation("androidx.activity:activity-compose:1.9.0")

    // Use the Compose Bill of Materials (BOM) to manage Compose library versions.
    // This ensures all Compose libraries use compatible versions.
    implementation(platform("androidx.compose:compose-bom:2024.06.00")) // Using the latest stable BOM (as of June 2025)

    // Declare Compose UI and Material3 dependencies without specifying versions
    // as they are managed by the BOM.
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(libs.ads.mobile.sdk)

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Compose testing dependencies (also managed by BOM)
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Debugging and tooling dependencies for Compose
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}