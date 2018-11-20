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

class Github(
    val owner: String,
    val repo: String,
    val token: String,
    val githubIssue: GithubIssue
) : Runnable {

    override fun run() {
        createIssue()
    }

    val client = OkHttpClient()
    val JSON = MediaType.parse("application/json; charset=utf-8")

    private fun createIssue() {
        val json = Adapters.GithubIssue.toJson(githubIssue)
        val requestBody = RequestBody.create(JSON, json)
        val request = createRequestHeaders(token)
            .url("https://api.github.com/repos/$owner/$repo/issues")
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
            if (status == HttpURLConnection.HTTP_NOT_FOUND) {
                error("404 Repository with Owner: '$owner' and Name: '$repo' was not found")
            }
            error("Could not create github issue: $status ${response?.message()}\n$githubIssue")
        }
    }

    private fun createRequestHeaders(token: String): Request.Builder {
        return Request.Builder()
            .addHeader("Authorization", "token $token")
            .addHeader("User-Agent", "delibs-gradle-plugin")
            .addHeader("Accept", "application/vnd.github.v3+json")
            .addHeader("Content-Type", "application/json")
    }
}
