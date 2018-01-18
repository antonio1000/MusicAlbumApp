package com.example.antoniolinguaglossa.musicalbumapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object BackendServiceRetrofit {

    fun obtain(
            readTimeoutInSeconds: Long = 30,
            connectTimeoutInSeconds: Long = 30
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return Retrofit.Builder()
                // .baseUrl("http://127.0.0.1/")
                .baseUrl("http://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                        OkHttpClient
                                .Builder()
                                .addInterceptor(loggingInterceptor)
                                .readTimeout(readTimeoutInSeconds, TimeUnit.SECONDS)
                                .connectTimeout(connectTimeoutInSeconds, TimeUnit.SECONDS)
                                .build()
                )
                .build()
    }

}