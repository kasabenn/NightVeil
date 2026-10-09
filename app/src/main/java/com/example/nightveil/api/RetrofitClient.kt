package com.example.nightveil.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL =
        "https://swdmnkrunxlqiabvufgk.supabase.co/"

    private const val SUPABASE_KEY =
        "sb_publishable_ZcasrHv8qGVJTwCz2t0seA_2c8NnNVG"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->

            val request = chain.request()
                .newBuilder()
                .addHeader("apikey", SUPABASE_KEY)
                .addHeader(
                    "Authorization",
                    "Bearer $SUPABASE_KEY"
                )
                .build()

            chain.proceed(request)
        }
        .build()

    val apiService: ApiService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}