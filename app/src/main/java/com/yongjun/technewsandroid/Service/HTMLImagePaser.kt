package com.yongjun.technewsandroid.Service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object HTMLParserManager {
    private val cache: MutableMap<URL, Bitmap> = mutableMapOf()
    private val parsedCache: MutableSet<URL> = mutableSetOf()

    suspend fun getWebPageImageUrl(urlString: String): String? {
        var url = urlString
        if (!url.startsWith("http")) {
            url = "https://$url"
        }

        val queryUrl = URL(url)
        return try {
            val data = withContext(Dispatchers.IO) {
                val connection = queryUrl.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                val response = StringBuilder()
                var inputLine: String?
                while (reader.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                reader.close()
                inputStream.close()
                response.toString().toByteArray(Charsets.UTF_8)
            }
            val contents = String(data!!, Charsets.UTF_8)
            val doc: Document = Jsoup.parse(contents)

            val link = doc.select("meta")

            val imageProperty = link.filter { element ->
                element.attr("property") == "og:image"
            }
            if (imageProperty.isNotEmpty()) {
                var imageUrl = imageProperty[0].attr("content")
                if (!(imageUrl.startsWith("http") || imageUrl.startsWith("www"))) {
                    imageUrl = url + imageUrl
                }
                imageUrl
            } else {
                val itemPropImage = link.filter { element ->
                    element.attr("itemprop") == "image"
                }.firstOrNull()
                itemPropImage?.let {
                    var imageUrl = it.attr("content")
                    if (!(imageUrl.startsWith("http") || imageUrl.startsWith("www"))) {
                        imageUrl = url + imageUrl
                    }
                    imageUrl
                } ?: run {
                    null
                }
            }
        } catch (e: IOException) {
            null
        }
    }

}

fun String.stripOutHtml(): String? {
    return try {
        val data = this.toByteArray(Charsets.UTF_8)
        val doc = Jsoup.parse(String(data, Charsets.UTF_8))
        doc.text()
    } catch (e: Exception) {
        null
    }
}
