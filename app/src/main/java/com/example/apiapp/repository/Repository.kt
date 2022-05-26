package com.example.apiapp.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

open class Repository(
    isDebugEnabled: Boolean,
) {

    private val apiKeyHeader: String = "x-api-key"
    val retrofit: Retrofit

    // public static fields in a companion object because im a horrible person
    companion object {
        // the server url endpoint
       private const val baseUrl = "https://api.thecatapi.com/v1/"
        // this is where you declare your api key
       private const val apiKey = "1ef90501-04e0-4592-9831-eb3e25d439b1"
    }


    init {

        /*adding a logging interceptor when debug is true.
        you can check how your API call is going in the LogCat */
        val loggingInterceptor = HttpLoggingInterceptor()
        if (isDebugEnabled) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        // here's how you can add your api key as a header
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(apiKeyHeader, apiKey)
                .build()
            chain.proceed(request)
        }.addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
