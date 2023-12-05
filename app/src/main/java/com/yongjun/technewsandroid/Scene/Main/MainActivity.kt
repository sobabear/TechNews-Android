package com.yongjun.technewsandroid.Scene.Main

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.yongjun.technewsandroid.CommonView.WebViewScreen
import com.yongjun.technewsandroid.Model.NewsItem
import com.yongjun.technewsandroid.Scene.Bottom.MainScreenView
import com.yongjun.technewsandroid.Scene.Setting.navSetting
import com.yongjun.technewsandroid.Service.HTMLParserManager
import com.yongjun.technewsandroid.ui.theme.TechNewsAndroidTheme
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechNewsAndroidTheme {
                MainScreenView()
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    NewsNavApp()
//                }
            }
        }
    }
}

@Composable
fun NewsNavApp() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    val newsItems = viewModel.newsItems.value

    NavHost(navController = navController, startDestination = "newsList") {
        composable("newsList") {
            NewsList(navController, newsItems)
        }
        composable("newsDetail/{newsId}") { backStackEntry ->
            val newsItemId = backStackEntry.arguments?.getString("newsId")
            val index = newsItems.indexOfFirst {
                it.id == newsItemId
            }
            val newsItem = newsItems.get(index = index)
            newsItem.url?.let { WebViewScreen(it) }

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewsList(
    nav: NavHostController,
    newsItems: List<NewsItem>
) {

    Column {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(newsItems) { item ->
                NewsItemCard(item, navController = nav)
            }
        }
    }
}


@Composable
fun NewsItemCard(newsItem: NewsItem, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("newsDetail/${newsItem.id}")
            },
            shape = RoundedCornerShape(16.dp),

        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            val imageBitmapState = remember { mutableStateOf<Bitmap?>(null) }
            val imageUrlState= remember {
                mutableStateOf<String?>(null)
            }

            LaunchedEffect(newsItem.url) {
                if (!newsItem.url.isNullOrEmpty()) {
                    val weburl = newsItem.url
                    val imageUrl = HTMLParserManager.getWebPageImageUrl(weburl)
                    imageUrlState.value = imageUrl
                }
            }
            AsyncImage(
                model = imageUrlState.value,
                contentDescription = newsItem.title,
                modifier = Modifier
                    .size(100.dp, 100.dp)
                    .background(LightGray, RoundedCornerShape(16))
                    .clip(RoundedCornerShape(16.dp)),
                contentScale =  ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Author: ${newsItem.author}",
                    style = MaterialTheme.typography.body2,
                    fontSize = 14.sp
                )

                Text(
                    text = "👍: ${newsItem.score}",
                    style = MaterialTheme.typography.body2,
                    fontSize = 14.sp
                )
                Text(
                    text = "${newsItem.time?.let { convertUnixTimestampToDateTime(it) }}",
                    style = MaterialTheme.typography.body2,
                    fontSize = 14.sp
                )
                Text (
                    text = "${newsItem.url}",
                    style = MaterialTheme.typography.caption,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun convertUnixTimestampToDateTime(timestamp: Long): String {
    // Convert Unix timestamp to a readable date and time format
    val instant = Instant.ofEpochSecond(timestamp)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    // Define a custom date and time format
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    return localDateTime.format(formatter)
}