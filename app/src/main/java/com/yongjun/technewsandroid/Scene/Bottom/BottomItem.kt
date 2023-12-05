package com.yongjun.technewsandroid.Scene.Bottom

const val NEWS = "News"
const val SETTING = "SETTING"

sealed class BottomItem(
    val title: String, val icon: String, val route: String
) {
    object News: BottomItem("News", "📰", NEWS)
    object Setting: BottomItem("Setting", "🍽️", SETTING)
}