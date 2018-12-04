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

import com.hellofresh.deblibs.Adapters.adapter
import com.hellofresh.deblibs.BaseClient
import com.hellofresh.deblibs.BaseClient.Companion.AUTHORIZATION
import com.hellofresh.deblibs.BaseClient.Companion.JSON
import com.hellofresh.deblibs.BaseClient.Companion.client
import com.hellofresh.deblibs.BaseClient.Companion.requestBuilder
import com.squareup.moshi.JsonAdapter
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class SlackClient(
    private val token: String,
    private val slackMessage: SlackMessage
) : BaseClient {

    private val moshiAdapter: JsonAdapter<SlackMessage> = adapter()

    override fun run() {
        postMessage()
    }

    private fun postMessage() {
        val json = moshiAdapter.toJson(slackMessage)
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

        val isSuccessful = response?.isSuccessful ?: return
        if (!isSuccessful) {
            error("Could not create slack message: $status ${response.message()}\n")
        }
    }

    private fun createRequestHeaders(token: String): Request.Builder {
        return requestBuilder.addHeader(AUTHORIZATION, "Bearer $token")
    }
}
