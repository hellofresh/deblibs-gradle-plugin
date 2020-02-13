/*
 * Copyright (c) 2018. The DebLibs Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        jcenter()
    }
}

plugins {
    id("com.gradle.plugin-publish") version "0.10.1"
    kotlin("jvm") version "1.3.41"
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    id("io.gitlab.arturbosch.detekt") version "1.0.0-RC16"
    id("org.jlleitschuh.gradle.ktlint") version "8.1.0"
}

apply {
    plugin("kotlin")
    plugin("org.jlleitschuh.gradle.ktlint")
}

repositories {
    jcenter()
}

group = "com.hellofresh.gradle"
version = "2.3.0-SNAPSHOT"

gradlePlugin {
    plugins {
        register("deblibs") {
            id = "com.hellofresh.gradle.deblibs"
            implementationClass = "com.hellofresh.deblibs.DebLibsPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/hellofresh/deblibs-gradle-plugin"
    vcsUrl = "https://github.com/hellofresh/deblibs-gradle-plugin"
    tags = listOf(
        "kotlin",
        "kotlin-dsl",
        "versioning",
        "dependencies",
        "libraries",
        "upgrade",
        "android",
        "maven",
        "gradle",
        "java",
        "gradle-version"
    )

    (plugins) {

        "deblibs" {
            displayName = "DebLibs"
            description = "Uploads apps dependencies to github"
        }
    }
}

fun setupPublishingEnvironment() {

    val keyProperty = "gradle.publish.key"
    val secretProperty = "gradle.publish.secret"

    if (System.getProperty(keyProperty) == null || System.getProperty(secretProperty) == null) {
        logger.info("`$keyProperty` or `$secretProperty` were not set. Attempting to configure from environment variables")

        val key: String? = System.getenv("GRADLE_PUBLISH_KEY")
        val secret: String? = System.getenv("GRADLE_PUBLISH_SECRET")
        if (!key.isNullOrBlank() && !secret.isNullOrBlank()) {
            System.setProperty(keyProperty, key)
            System.setProperty(secretProperty, secret)
        } else {
            logger.warn("key or secret was null")
        }
    }
}

setupPublishingEnvironment()

dependencies {
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.9")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.27.0")
    implementation("com.squareup.okhttp3:okhttp:4.3.1")
    implementation("com.squareup.moshi:moshi:1.9.2")
    implementation("com.squareup.moshi:moshi-kotlin:1.9.2")
    implementation("com.squareup.okio:okio:2.4.3")
    implementation(kotlin("stdlib"))
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

detekt {
    input = files("src/main/kotlin")
    filters = ".*/resources/.*,.*/build/.*"
}
