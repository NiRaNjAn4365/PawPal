package com.example.pawpal.network

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Base64
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

    fun uploadToImgBB(context:Context,imageUri:Uri,onUpload:(String)->Unit){
        val apiKey="7bf61148bdd5703d6f692fa728befd0e"
        val inputStream=context.contentResolver.openInputStream(imageUri)
        val imageBytes=inputStream?.readBytes()
        inputStream?.close()

        val base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT)

        val requestBody= FormBody.Builder().add("key",apiKey)
            .add("image",base64Image)
            .build()
        val request = Request.Builder()
            .url("https://api.imgbb.com/1/upload")
            .post(requestBody)
            .build()
        val client= OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val json = JSONObject(responseBody ?: "")
                val url = json.getJSONObject("data").getString("url")
                Handler(Looper.getMainLooper()).post {
                    onUpload(url)
                }
            }
        })    }
