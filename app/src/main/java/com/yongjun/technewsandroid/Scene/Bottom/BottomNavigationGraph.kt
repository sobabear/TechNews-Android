package com.yongjun.technewsandroid.Scene.Bottom

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yongjun.technewsandroid.Scene.Main.NewsNavApp
import com.yongjun.technewsandroid.Scene.Setting.SettingList
import com.yongjun.technewsandroid.Scene.Setting.SettingViewModel
import com.yongjun.technewsandroid.Scene.Setting.navSetting

@Composable
fun BottomNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomItem.News.route) {
        composable(BottomItem.News.route) {
            NewsNavApp()
        }
        composable(BottomItem.Setting.route) {
            testNav(nav = navController)
        }
    }
}

@Composable
fun testNav(nav: NavHostController) {
    val viewModel: SettingViewModel = viewModel()
    SettingList(nav = nav, items = viewModel.settingItems.value)
}