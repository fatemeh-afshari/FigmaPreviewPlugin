package com.figma.preview.figmapreview
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class FigmaService(private val accessToken: String) {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun getComponentImage(fileId: String, nodeId: String): String? {
        val request = Request.Builder()
            .url("https://api.figma.com/v1/images/$fileId?ids=$nodeId&format=png")
            .addHeader("X-Figma-Token", accessToken)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return "error"

            val jsonResponse = gson.fromJson(response.body?.string(), Map::class.java)
            return (jsonResponse["images"] as Map<*, *>)[nodeId.replace("-" , ":")] as String?
        }
    }

    fun getDesignTokens(fileId: String): Map<String, Any>? {
        val request = Request.Builder()
            .url("https://api.figma.com/v1/files/$fileId")
            .addHeader("X-Figma-Token", accessToken)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null

            val jsonResponse = gson.fromJson(response.body?.string(), Map::class.java)
            return (jsonResponse["document"] as Map<*, *>)["designTokens"] as Map<String, Any>?
        }
    }
}