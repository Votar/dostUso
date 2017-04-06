package com.entrego.entregouser.web.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiCreator {
    private val server: Retrofit

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //Logger interceptor
        val client = OkHttpClient.Builder()
        client.addInterceptor(loggingInterceptor)
        client.writeTimeout(20, TimeUnit.SECONDS)
        client.readTimeout(20, TimeUnit.SECONDS)
        client.connectTimeout(20, TimeUnit.SECONDS)

        server = Retrofit.Builder()
                .baseUrl(EntregoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
    }

    fun get() = server
}