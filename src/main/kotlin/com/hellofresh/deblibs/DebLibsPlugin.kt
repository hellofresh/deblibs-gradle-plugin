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

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

open class DebLibsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val gradlePlugionVersions: DependencyUpdatesTask =
            project.tasks.maybeCreate("dependencyUpdates", DependencyUpdatesTask::class.java)

        with(gradlePlugionVersions) {
            outputFormatter = "json"
            checkForGradleUpdate = true
            resolutionStrategy {
                componentSelection {
                    all {
                        val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview")
                            .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-]*") }
                            .any { it.matches(candidate.version) }
                        if (rejected) {
                            reject("Release candidate")
                        }
                    }
                }
            }
        }

        val ext = project.extensions.create("deblibs", DebLibsExtension::class.java)
        project.tasks.create("createGithubIssue", CreateGithubIssueTask::class) {
            dependsOn(":dependencyUpdates")
            jsonInputPath = "${gradlePlugionVersions.outputDir}/${gradlePlugionVersions.reportfileName}.json"
        }

        project.afterEvaluate {
            val debLibsPlugin = project.plugins.findPlugin(DebLibsPlugin::class.java)
            if (debLibsPlugin is DebLibsPlugin) {
                val createGithubIssue = project.tasks.getByName("createGithubIssue") as CreateGithubIssueTask
                //TODO set default values based on project config
                createGithubIssue.owner = ext.owner
                createGithubIssue.repo = ext.repo
                createGithubIssue.token = ext.token
            }
        }
    }
}
