/*
 * Copyright (c) 2018 The DebLibs Authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.hellofresh.deblibs

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

interface BaseClient : Runnable {

    companion object {

        val client = OkHttpClient()
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        const val AUTHORIZATION = "Authorization"
        val requestBuilder: Request.Builder
            get() {
                return Request.Builder()
                    .addHeader("User-Agent", "deblibs-gradle-plugin")
                    .addHeader("Content-Type", "application/json")
            }
    }

    fun postRequest(
        json: String,
        url: String,
        headerName: String,
        headerValue: String
    ): Pair<Response?, Int> {
        val requestBody = RequestBody.create(JSON, json)
        val request = requestBuilder.addHeader(headerName, headerValue)
            .url(url)
            .post(requestBody)
            .build()
        var response: Response? = null
        val status: Int

        try {
            response = client.newCall(request).execute()
            status = response.code()
        } finally {
            response?.close()
        }
        return Pair(response, status)
    }
}
