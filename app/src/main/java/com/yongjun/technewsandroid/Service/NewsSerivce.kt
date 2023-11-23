package com.yongjun.technewsandroid.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsService {
    private const val BASE_URL = "https://us-central1-hnbot-dbec4.cloudfunctions.net/"

    val api: NewsApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(NewsApi::class.java)
    }
}
