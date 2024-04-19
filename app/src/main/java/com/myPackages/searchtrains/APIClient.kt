package com.myPackages.searchtrains

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class APIClient {

    private val client: OkHttpClient = OkHttpClient()

    @Throws(IOException::class)
    fun search(searchQuery: String): String {
        val mediaType = "application/json".toMediaTypeOrNull()

        val requestBody = RequestBody.create(mediaType, "{\"search\": \"$searchQuery\"}")

        val request = Request.Builder()
            .url("https://trains.p.rapidapi.com/")
            .post(requestBody)
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", "c9767a8c33msh00d4dabd227c8a6p15468cjsnd23166c1f3ba")
            .addHeader("X-RapidAPI-Host", "trains.p.rapidapi.com")
            .build()

        val response = client.newCall(request).execute()
        return response.body?.string() ?: throw IOException("Failed to get response body")
    }

    companion object {
        @Volatile private var instance: APIClient? = null

        fun getInstance(): APIClient {
            return instance ?: synchronized(this) {
                instance ?: APIClient().also { instance = it }
            }
        }
    }
}
