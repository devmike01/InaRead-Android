package dev.gbenga.inaread.adapters

import com.google.gson.Gson
import dev.gbenga.inaread.data.model.ApiResult
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class NetworkResponseInterceptor constructor(private val gson: Gson) : Interceptor {

    companion object{
        val APPLICATION_JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return try {
            chain.proceed(request)
            // TODO: Handle api error
        }catch (exception: Exception){
            val body = gson.toJson(ApiResult<Any>(error = "${exception.message}"))
            val responseBody: ResponseBody = body.toResponseBody(APPLICATION_JSON)

            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(500)
                .message("Internal Server Error")
                .body(responseBody)
                .build()
        }

    }
}