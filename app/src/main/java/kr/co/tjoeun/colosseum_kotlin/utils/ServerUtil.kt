package kr.co.tjoeun.colosseum_kotlin.utils

import android.content.Context
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    interface JsonResponseHandler {
        fun onResponse(json : JSONObject)
    }

    companion object {

        private val BASE_URL = "http://15.165.177.142"

        fun postRequestLogin(context: Context, email: String, pw: String, handler: JsonResponseHandler) {

            val client = OkHttpClient()

            val formBody = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

            val request = Request.Builder()
                .url("${BASE_URL}/user")
                .post(formBody)
//                .header()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body!!.string()

                    val jsonObj = JSONObject(body)

                    handler.onResponse(jsonObj)

                }

            })

        }

        fun getRequestMainInfo(context: Context, handler: JsonResponseHandler) {

            val client = OkHttpClient()

            val urlBuilder = "${BASE_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("device_token", "임시토큰")
            urlBuilder.addEncodedQueryParameter("os", "Android")

            val urlStr = urlBuilder.build().toString()

            val request = Request.Builder()
                .url(urlStr)
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .get()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body!!.string()

                    val jsonObj = JSONObject(body)

                    handler.onResponse(jsonObj)

                }

            })

        }

    }

}