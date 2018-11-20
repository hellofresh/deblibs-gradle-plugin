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

val kotlinVersion: String by extra
buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.3.0"
    repositories {
        mavenCentral()
    }
}

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.10.0"
}
apply {
    plugin("kotlin")
}

group = "com.hellofresh.deblibs"
version = "0.1.0"

gradlePlugin {
    plugins {
        register("deblibs") {
            id = "com.hellofresh.deblibs"
            displayName = "deblibs"
            description = "Uploads apps dependencies to github"
            implementationClass = "com.hellofresh.deblibs.DebLibsPlugin"
        }
    }
}

publishing {
    repositories {
        maven(url = "build/repository")
    }

    publications {
        register("deblibs", MavenPublication::class) {
            setGroupId(groupId)
            artifactId = "deblibs"
            from(components["java"])
        }
    }
}

repositories {
    jcenter()
}

pluginBundle {
    website = "https://github.com/hellofresh/deblibs"
    vcsUrl = "https://github.com/hellofresh/deblibs"
    tags = listOf("kotlin", "kotlin-dsl", "versioning")
}
dependencies {
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.9")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.20.0")
    implementation("com.squareup.okhttp3:okhttp:3.8.1")
    implementation("com.squareup.moshi:moshi:1.7.0")
    implementation("com.squareup.okio:okio:2.1.0")
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
