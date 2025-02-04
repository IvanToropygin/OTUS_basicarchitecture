package com.sumin.otus_basicarchitecture.network

import com.sumin.otus_basicarchitecture.network.models.SuggestionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://suggestions.dadata.ru/"

interface DadataService {

    @GET("suggestions/api/4_1/rs/suggest/address")
    suspend fun suggestAddress(@Query("query") query: String): SuggestionsResponse
}
