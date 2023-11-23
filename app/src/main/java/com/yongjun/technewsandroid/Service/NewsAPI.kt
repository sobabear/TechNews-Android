package com.yongjun.technewsandroid.Service

import com.yongjun.technewsandroid.Model.NewsItem
import retrofit2.http.GET

interface NewsApi {
    @GET("hn-backend")
    suspend fun getNews(): List<NewsItem>
}
