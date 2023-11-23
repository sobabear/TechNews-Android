package com.yongjun.technewsandroid

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yongjun.technewsandroid.Model.NewsItem
import com.yongjun.technewsandroid.ui.theme.TechNewsAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechNewsAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NewsApp()
                }
            }
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewsApp() {
    val viewModel: MainViewModel = viewModel()
    val newsItems = viewModel.newsItems.value

    Column {

        LazyColumn {
            items(newsItems) { item ->
                NewsItemCard(item)
            }
        }
    }
}


@Composable
fun NewsItemCard(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
//            .clickable { /* Handle click on the news item if needed */ },
        elevation = 4.dp
    ) {
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
                text = "Score: ${newsItem.score}",
                style = MaterialTheme.typography.body2,
                fontSize = 14.sp
            )

            Text(
                text = "Time: ${convertUnixTimestampToDateTime(newsItem.time)}",
                style = MaterialTheme.typography.body2,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = newsItem.url,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .fillMaxWidth()
//                    .clickable { /* Handle click on the URL if needed */ }
            )
        }
    }
}

@Composable
private fun convertUnixTimestampToDateTime(timestamp: Long): String {
    // Convert Unix timestamp to a readable date and time format
    // You can use your preferred date and time formatting logic here
    return ""
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TechNewsAndroidTheme {
        Greeting("Android")
    }
}