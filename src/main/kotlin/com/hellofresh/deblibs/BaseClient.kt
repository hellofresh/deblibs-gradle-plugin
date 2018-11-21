package com.hellofresh.deblibs

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request

interface BaseClient : Runnable {

    companion object {

        val client = OkHttpClient()
        val JSON = MediaType.parse("application/json; charset=utf-8")
        const val AUTHORIZATION = "Authorization"
        val requestBuilder: Request.Builder
            get() {
                return Request.Builder()
                    .addHeader("User-Agent", "deblibs-gradle-plugin")
                    .addHeader("Content-Type", "application/json")
            }
    }
}
