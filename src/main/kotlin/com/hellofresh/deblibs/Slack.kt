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

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.net.HttpURLConnection

class Slack(
    val username: String,
    val token: String,
    val slackMessage: SlackMessage
) : Runnable {

    override fun run() {
        postMessage()
    }

    val client = OkHttpClient()
    val JSON = MediaType.parse("application/json; charset=utf-8")

    private fun postMessage() {
        val json = Adapters.SlackMessage.toJson(slackMessage)
        val requestBody = RequestBody.create(JSON, json)
        val request = createRequestHeaders(token)
            .url("https://slack.com/api/chat.postMessage")
            .post(requestBody)
            .build()
        var response: Response? = null
        var status: Int
        try {
            response = client.newCall(request).execute()
            status = response.code()
        } finally {
            response?.body()?.close()
        }

        if (status != HttpURLConnection.HTTP_CREATED) {
            error("Could not create slack message: $status ${response?.message()}\n")
        }
    }

    private fun createRequestHeaders(token: String): Request.Builder {
        return Request.Builder()
            .addHeader("Authorization", "Bearer $token")
            .addHeader("User-Agent", "delibs-gradle-plugin")
            .addHeader("Content-Type", "application/json")
    }
}
