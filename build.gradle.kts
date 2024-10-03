buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {

        classpath("com.android.tools.build:gradle:8.5.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.25")
        classpath("com.google.gms:google-services:4.3.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

//tasks.register('clean', Delete) {
//    delete rootProject.buildDir
//}
