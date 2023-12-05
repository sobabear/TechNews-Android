package com.yongjun.technewsandroid.Scene.Bottom

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yongjun.technewsandroid.Scene.Main.NewsNavApp
import com.yongjun.technewsandroid.Scene.Setting.setting

@Composable
fun BottomNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomItem.News.route) {
        composable(BottomItem.News.route) {
            NewsNavApp()
        }
        composable(BottomItem.Setting.route) {
            setting()
        }
    }
}