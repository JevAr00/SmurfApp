package com.example.summoners.api

import okhttp3.Interceptor
import okhttp3.Response

class RequestedHeaders : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Riot-Token", "SOME-RIOT-TOKEN")
            .build()
        return chain.proceed(request)
    }
}