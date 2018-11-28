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

import org.gradle.internal.time.Time

data class SlackMessage(
    val username: String,
    val channel: String,
    val icon_url: String = DEFAULT_ICON_URL,
    val attachments: List<Attachment>
) {
    data class Attachment(
        val color: String = Color.YELLOW.hexColor,
        val title: String,
        val pretext: String,
        val text: String,
        val footer: String,
        val ts: Long = Time.currentTimeMillis().div(1000)
    ) {
        enum class Color(val hexColor: String) {
            RED("#c4291c"),
            GREEN("#36a64f"),
            YELLOW("#d6a048")
        }
    }

    companion object {
        const val DEFAULT_ICON_URL = "https://avatars.slack-edge.com/2018-06-12/380095389394_a485f66ceffe05c1e6d4_192.png"
    }
}
