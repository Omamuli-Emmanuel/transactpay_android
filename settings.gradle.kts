pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven { url = uri ("https://www.jitpack.io") }
    }
}
include(":app")
include(":transactpay_android")