package com.yongjun.technewsandroid

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yongjun.technewsandroid.Model.NewsItem
import com.yongjun.technewsandroid.Service.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val newsApi = NewsService.api

    val newsItems = mutableStateOf<List<NewsItem>>(emptyList())

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = newsApi.getNews()
                newsItems.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
