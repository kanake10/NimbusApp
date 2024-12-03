package com.example.weatherupdates.data.remote.utils

import okhttp3.Interceptor
import okhttp3.Response


class Interceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("key", "a236ebb90d17481da56144752240212")
            .addHeader("q", "Nairobi")
            .build()
        return chain.proceed(request)
    }
}