package com.sumin.otus_basicarchitecture.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("AuthInterceptor", "API Key: $apiKey")
        val request = chain.request()
        Log.d("AuthInterceptor", "Intercepting request: ${request.url}")

        val requestWithAuth = request.newBuilder()
            .header("Authorization", "Token $apiKey")
            .build()

        return chain.proceed(requestWithAuth)
    }
}