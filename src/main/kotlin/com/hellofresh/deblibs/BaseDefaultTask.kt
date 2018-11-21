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
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class BaseDefaultTask : DefaultTask() {

    @Input
    var jsonInputPath = "build/dependencyUpdates/report.json"

    protected abstract fun postTask(dependencyGraph: DependencyGraph)

    @TaskAction
    fun taskAction() {
        val jsonInput = project.file(jsonInputPath)
        val dependencyGraph = readGraphFromJsonFile(jsonInput)
        postTask(dependencyGraph)
    }

    protected fun parseGraphForGradle(graph: DependencyGraph): String {
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

    protected fun parseGraph(graph: DependencyGraph): List<Dependency> {
        val dependencies: List<Dependency> = graph.outdated
        return dependencies.sortedDependencies()
    }

    private fun readGraphFromJsonFile(jsonInput: File): DependencyGraph {
        return Adapters.DependencyGraph.fromJson(jsonInput.source().buffer())!!
    }

    private fun List<Dependency>.sortedDependencies(): List<Dependency> {
        return this.sortedBy { it.gradleNotation() }
    }

    private fun escapeName(name: String): String {
        return name.replace("[-.:]".toRegex(), "_").toLowerCase()
    }
}
