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

package com.hellofresh.deblibs.github

import com.hellofresh.deblibs.BaseDefaultTask
import com.hellofresh.deblibs.Dependency
import com.hellofresh.deblibs.DependencyGraph
import com.hellofresh.deblibs.gradleNotation
import org.gradle.api.tasks.Input

open class CreateGithubIssueTask : BaseDefaultTask() {

    @Input
    lateinit var repo: String
    @Input
    lateinit var token: String

    init {
        description = "Create Github issue from the outdated dependencies report."
        group = "reporting"
    }

    protected override fun postTask(dependencyGraph: DependencyGraph) {
        val dependencies: List<Dependency> = parseGraph(dependencyGraph)
        var outDatedDependencies = ""
        dependencies.forEach {
            val projectUrl = if (it.projectUrl != null) " -> [${it.group}](${it.projectUrl})" else ""
            outDatedDependencies += "* [ ] `${it.gradleNotation()}`$projectUrl\n"
        }
        val gradleVersion = parseGraphForGradle(dependencyGraph)
        if (gradleVersion.isNotBlank()) {
            outDatedDependencies += "\nGradle updates:\n* [ ] `Gradle: $gradleVersion`\n"
        }
        if (outDatedDependencies.isNotBlank()) {
            GithubClient(
                repo,
                token,
                GithubIssue("Outdated Dependencies(${dependencies.size})", outDatedDependencies)
            ).run()
        }
    }
}
