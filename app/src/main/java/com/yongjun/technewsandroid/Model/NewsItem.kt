package com.yongjun.technewsandroid.Model

data class NewsItem(
    val title: String,
    val url: String?,
    val score: Int?,
    val author: String?,
    val time: Long?,
    val id: String
)
