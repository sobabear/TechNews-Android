package com.yongjun.technewsandroid.Scene.Setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yongjun.technewsandroid.CommonView.WebViewScreen
import com.yongjun.technewsandroid.Model.NewsItem
import com.yongjun.technewsandroid.Scene.Main.NewsItemCard
import com.yongjun.technewsandroid.Scene.Main.NewsList

@Composable
fun SettingList(
    nav: NavHostController,
    items: List<SettingItem>
) {

    Column {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items) { item ->
                SettingCard(item, nav)
            }
        }
    }
}
@Composable
fun navSetting() {
    val navController = rememberNavController()
    val viewModel = SettingViewModel()

    NavHost(navController = navController, startDestination = "navSetting") {
        composable("settingList") {
//            Text("aaa")
            SettingList(nav = navController, viewModel.settingItems.value)
        }
    }
}

@Composable
fun SettingCard(settingItem: SettingItem, nav: NavHostController) {
    Card(
        Modifier
        .fillMaxWidth(),
         RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = settingItem.icon)

            Spacer(modifier = Modifier.width(8.dp))
            Text(text = settingItem.title)
            Spacer(Modifier.fillMaxWidth())
            settingItem.rightString?.let {
                Text(text = it)
            }
        }
    }
}