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

import org.gradle.api.DefaultTask

abstract class BaseDefaultTask : DefaultTask() {

    protected fun List<Dependency>.findCommonVersions(): List<Dependency> {
        val map = groupBy { it.group }
        for (deps in map.values) {
            val groupTogether = deps.size > 1 && deps.map { it.version }.distinct().size == 1
            for (d in deps) {
                d.versionName = if (groupTogether) escapeName(d.group) else d.escapedName
            }
        }
        return this
    }

    protected fun List<Dependency>.orderDependencies(): List<Dependency> {
        return this.sortedBy { it.gradleNotation() }
    }

    protected fun escapeName(name: String): String {
        val escapedChars = listOf('-', '.', ':')
        return buildString {
            for (c in name) {
                append(if (c in escapedChars) '_' else c.toLowerCase())
            }
        }
    }

    protected fun parseGraph(graph: DependencyGraph): List<Dependency> {
        val dependencies: List<Dependency> = graph.outdated
        return dependencies.orderDependencies().findCommonVersions()
    }
}
