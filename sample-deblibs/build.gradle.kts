import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Taken from versions.properties file

plugins {
    kotlin("jvm") version "1.3.41"
    id("com.hellofresh.gradle.deblibs") version "2.2.0-SNAPSHOT"
}

apply {
    plugin("kotlin")
}

repositories {
    jcenter()
}

group = "com.hellofresh.gradle.deblibs.sample"

buildscript {
    repositories {
        jcenter()
    }
}

dependencies {
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.9")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.27.0")
    implementation("com.squareup.okhttp3:okhttp:4.3.1")
    implementation("com.squareup.moshi:moshi:1.9.2")
    implementation("com.squareup.okio:okio:2.4.3")
    implementation(kotlin("stdlib"))
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

deblibs {
    projectName = project.name
    githubRepo = "hellofresh/deblibs-gradle-plugin"
    githubToken = System.getenv("GITHUB_TOKEN") ?: ""
    slackToken = System.getenv("SLACK_TOKEN") ?: ""
    slackChannel = System.getenv("SLACK_CHANNEL") ?: ""
    slackIconUrl = System.getenv("SLACK_ICON_URL") ?: ""
    gitlabToken = System.getenv("GITLAB_TOKEN") ?: ""
    gitlabProjectId = System.getenv("GITLAB_PROJECT_ID") ?: ""
}
