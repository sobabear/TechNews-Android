package com.yongjun.technewsandroid.Scene.Setting

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingViewModel: ViewModel() {
    val settingItems = mutableStateOf(
        listOf(
            SettingItem("Contact", "💌", "aringod7@gmail.com", null),
            SettingItem("Version", "🔖", "1.1", null)
        )
    )
}

data class SettingItem(
    val title: String,
    val icon: String,
    val rightString: String?,
    val route: String?

)
