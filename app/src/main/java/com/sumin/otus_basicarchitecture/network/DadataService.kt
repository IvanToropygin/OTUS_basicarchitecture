package com.sumin.otus_basicarchitecture.network

import com.sumin.otus_basicarchitecture.network.models.SuggestionsResponse
import dadata_key.API_KEY
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://suggestions.dadata.ru/"

interface DadataService {
    @GET("suggestions/api/4_1/rs/suggest/address")
    suspend fun suggestAddress(
        @Query("query") query: String
    ): SuggestionsResponse
}

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor(API_KEY))
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    })
    .build()

val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(DadataService::class.java)

