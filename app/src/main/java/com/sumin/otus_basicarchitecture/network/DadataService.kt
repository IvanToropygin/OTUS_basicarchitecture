package com.sumin.otus_basicarchitecture.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val BASE_URL = "https://suggestions.dadata.ru/"

interface DadataService {
    @GET("suggestions/api/4_1/rs/suggest/address")
    suspend fun suggestAddress(
        @Query("query") query: String,
        @Header("Authorization") token: String,
    ): SuggestionsResponse
}

val retrofit: DadataService = Retrofit.Builder()
    .client(
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .also { it.level = HttpLoggingInterceptor.Level.BODY })
            .build()

    )
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(DadataService::class.java)

data class SuggestionsResponse(val suggestions: List<AddressSuggestion>)

data class AddressSuggestion(val value: String)