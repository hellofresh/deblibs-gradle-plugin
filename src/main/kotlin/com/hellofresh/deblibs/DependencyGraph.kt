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

package com.hellofresh.deblibs

data class DependencyGraph(
    val gradle: GradleConfig,
    val current: Dependencies,
    val exceeded: Dependencies,
    val outdated: Dependencies,
    val unresolved: Dependencies,
    val count: Int = 0
)

fun Dependency.gradleNotation(): String {
    val milestone = available?.milestone ?: return ""
    return "$group:$name:[$version -> $milestone]"
}

data class Dependencies(
    val dependencies: List<Dependency> = emptyList(),
    val count: Int = 0
) : List<Dependency> by dependencies

data class Dependency(
    val group: String = "",
    val version: String = "",
    val reason: String? = "",
    var latest: String? = "",
    val projectUrl: String? = "",
    val name: String = "",
    var escapedName: String = "",
    var versionName: String = "",
    val available: AvailableDependency? = null
)

data class GradleConfig(
    val current: GradleVersion,
    val nightly: GradleVersion,
    val enabled: Boolean = false,
    val releaseCandidate: GradleVersion,
    val running: GradleVersion
)

data class GradleVersion(
    val version: String = "",
    val reason: String = "",
    val isUpdateAvailable: Boolean = false,
    val isFailure: Boolean = false
)

data class AvailableDependency(
    val release: String? = "",
    val milestone: String? = "",
    val integration: String? = ""
)
