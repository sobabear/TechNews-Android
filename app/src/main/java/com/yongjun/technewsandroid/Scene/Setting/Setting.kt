package com.yongjun.technewsandroid.Scene.Setting

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun setting() {
    Text(text = "hellohello")
}

@Composable
fun navSetting() {
    val navController = rememberNavController()
}