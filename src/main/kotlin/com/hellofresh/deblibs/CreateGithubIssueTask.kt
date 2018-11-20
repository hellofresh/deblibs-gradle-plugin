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

import okio.buffer
import okio.source
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

open class CreateGithubIssueTask : BaseDefaultTask() {

    @Input
    var jsonInputPath = "build/dependencyUpdates/report.json"
    @Input
    lateinit var owner: String
    @Input
    lateinit var repo: String
    @Input
    lateinit var token: String

    init {
        description = "Create Github issue with a report of outdated dependencies"
        group = "reporting"
    }

    @TaskAction
    fun taskAction() {
        val jsonInput = project.file(jsonInputPath)
        val dependencyGraph = readGraphFromJsonFile(jsonInput)
        postToGithub(dependencyGraph)
    }

    private fun postToGithub(dependencyGraph: DependencyGraph) {
        val dependencies: List<Dependency> = parseGraph(dependencyGraph)
        var outDatedDependencies = ""
        dependencies.forEach {
            val projectUrl = if (it.projectUrl != null) " -> [${it.group}](${it.projectUrl})" else ""
            outDatedDependencies += "* [ ] `${it.gradleNotation()}`$projectUrl\n"
        }
        val gradleVersion = parseGraphForGradle(dependencyGraph)
        if (gradleVersion.isNotBlank()) {
            outDatedDependencies += "\nGradle updates:\n"
            outDatedDependencies += "* [ ] `Gradle: $gradleVersion`\n"
        }
        if (outDatedDependencies.isNotBlank()) {
            Github(
                owner,
                repo,
                token,
                GithubIssue("Outdated Dependencies(${dependencies.size})", outDatedDependencies)
            ).run()
        }
    }

    private fun parseGraphForGradle(graph: DependencyGraph): String {
        val gradle = graph.gradle
        return when {
            gradle.current.version > gradle.running.version -> {
                "[${gradle.running.version} -> ${gradle.current.version}]"
            }
            gradle.releaseCandidate.version > gradle.running.version -> {
                "[${gradle.running.version} -> ${gradle.releaseCandidate.version}]"
            }
            else -> ""
        }
    }

    private fun readGraphFromJsonFile(jsonInput: File): DependencyGraph {
        return Adapters.DependencyGraph.fromJson(jsonInput.source().buffer())!!
    }
}
