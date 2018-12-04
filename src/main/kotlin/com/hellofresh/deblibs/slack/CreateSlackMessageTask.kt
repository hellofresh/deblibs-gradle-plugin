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

package com.hellofresh.deblibs.slack

import com.hellofresh.deblibs.BaseDefaultTask
import com.hellofresh.deblibs.Dependency
import com.hellofresh.deblibs.DependencyGraph
import com.hellofresh.deblibs.gradleNotation
import com.hellofresh.deblibs.slack.SlackMessage.Attachment
import com.hellofresh.deblibs.slack.SlackMessage.Attachment.Color.GREEN
import com.hellofresh.deblibs.slack.SlackMessage.Attachment.Color.RED
import com.hellofresh.deblibs.slack.SlackMessage.Attachment.Color.YELLOW
import com.hellofresh.deblibs.slack.SlackMessage.Companion.DEFAULT_ICON_URL
import org.gradle.api.tasks.Input

open class CreateSlackMessageTask : BaseDefaultTask() {

    @Input
    lateinit var channel: String
    @Input
    lateinit var token: String
    @Input
    lateinit var username: String
    @Input
    lateinit var iconUrl: String
    @Input
    lateinit var projectName: String

    init {
        description = "Create a slack message from the outdated dependencies report."
        group = "reporting"
    }

    protected override fun postTask(dependencyGraph: DependencyGraph) {
        val dependencies: List<Dependency> = parseGraph(dependencyGraph)
        var outDatedDependencies = ""

        dependencies.forEach {
            outDatedDependencies += "- ${it.gradleNotation()}\n"
        }
        val gradleVersion = parseGraphForGradle(dependencyGraph)
        if (gradleVersion.isNotBlank()) {
            outDatedDependencies += "\n*Gradle updates:*\n\n`$gradleVersion`\n"
        }
        if (outDatedDependencies.isBlank()) {
            return
        }
        val attachments =
            buildMessageAttachment(dependencies.size, outDatedDependencies)
        val name = if (username.isBlank()) "DebLibs" else username
        val icon = if (iconUrl.isBlank()) DEFAULT_ICON_URL else iconUrl
        SlackClient(
            token,
            SlackMessage(
                username = name,
                channel = channel,
                icon_url = icon,
                attachments = attachments)
        ).run()
    }

    private fun buildMessageAttachment(dependencySize: Int, outDatedDependencies: String): List<Attachment> {
        val color = when {
            dependencySize <= 10 -> GREEN
            dependencySize <= 20 -> YELLOW
            else -> RED
        }
        return listOf(
            Attachment(
                color = color.hexColor,
                title = "Outdated Dependencies",
                pretext = projectName,
                text = "```$outDatedDependencies```",
                footer = "Total: $dependencySize"
            )
        )
    }
}
