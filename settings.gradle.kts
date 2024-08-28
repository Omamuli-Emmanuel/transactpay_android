pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri ("https://www.jitpack.io") }
        maven { url = uri ("https://maven.google.com") }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven { url = uri ("https://www.jitpack.io") }
        maven { url = uri ("https://maven.google.com") }
    }
}
include(":app")
include(":mylibrary")
include(":transactpay_android")
