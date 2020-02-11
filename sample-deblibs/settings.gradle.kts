val deblibs: String = "2.2.0-SNAPSHOT"

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
    }
}

buildscript {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "sample-deblibs"
includeBuild("../")
