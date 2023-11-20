package io.github.kabirnayeem99.vindecoder.src

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class HTTP {
    private val client by lazy { OkHttpClient() }
    private val gson by lazy { Gson() }

    fun <T> get(url: String, responseType: Class<T>): T? {
        try {
            val request = Request.Builder().url(url).build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                return gson.fromJson(responseBody, responseType)
            } else {
                println("Failed to make API call. Response Code: ${response.code}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Exception occurred: ${e.message}")
        }
        return null
    }

    fun get(url: String): Map<String, Any?>? {
        try {
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                return gson.fromJson(responseBody, Map::class.java) as Map<String, Any?>
            } else {
                println("Failed to make API call. Response Code: ${response.code}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Exception occurred: ${e.message}")
        }
        return null
    }
}