// C:\Users\johnw\StudioProjects\TodoAppCompose\app\build.gradle.kts

plugins {
    // Apply plugins from libs.versions.toml using 'alias'
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // If you removed kotlin-compose from libs.versions.toml, remove this line:
    // alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.johnw.todoappcompose"
    compileSdk = 35 // Target API 35
    var targetSdk = 35 // Target API 35

    defaultConfig {
        applicationId = "com.johnw.todoappcompose"
        minSdk = 24 // Minimum API Level
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        compose = true
    }

    composeOptions {
        // Reference composeCompiler version from libs.versions.toml
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Reference libraries from libs.versions.toml
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Use Compose BOM for all Compose UI and Material3 dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // WorkManager
    implementation(libs.androidx.work.runtime)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose testing dependencies (managed by BOM)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // ads-mobile-sdk (if included)
    // implementation(libs.ads.mobile.sdk)
}