package com.sumin.otus_basicarchitecture.di

import com.sumin.otus_basicarchitecture.BuildConfig
import com.sumin.otus_basicarchitecture.network.AuthInterceptor
import com.sumin.otus_basicarchitecture.network.BASE_URL
import com.sumin.otus_basicarchitecture.network.DadataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiKey(): String {
        return BuildConfig.DADATA_API_KEY
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(apiKey: String): AuthInterceptor {
        return AuthInterceptor(apiKey)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDadataService(retrofit: Retrofit): DadataService {
        return retrofit.create(DadataService::class.java)
    }
}