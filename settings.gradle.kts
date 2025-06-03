// C:\Users\johnw\StudioProjects\TodoAppCompose\settings.gradle.kts

// This block defines repositories for plugins themselves
pluginManagement {
    repositories {
        gradlePluginPortal() // Required for finding Gradle plugins
        google() // For Android Gradle Plugin and other Google-developed plugins
        mavenCentral() // For other plugins
    }
}

// This block defines repositories for dependencies of your modules (e.g., app, library modules)
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Enforces that project-level repos are forbidden
    repositories {
        google() // For AndroidX libraries and other Google artifacts
        mavenCentral() // For most other common libraries
    }
}

rootProject.name = "TodoAppCompose" // Your root project name

// Include your application modules here
include(":app")
// If you had other modules, you'd include them like:
// include(":your_library_module")