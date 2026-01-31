package dev.gbenga.inaread.adapters

import com.google.gson.Gson
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.tokens.StringTokens
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException

@Deprecated(replaceWith = ReplaceWith(expression = ""), message = "To be removed. No longer needed")
class NetworkResponseInterceptor constructor(private val gson: Gson) : Interceptor {

    companion object{
        val APPLICATION_JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return try {
            chain.proceed(request)
            // TODO: Handle api error
        }catch (exception: Exception ){
            // TODO: Log message to firebase crashlytics
            val body = gson.toJson(ApiResult<Any>(error = StringTokens.UnknownErrorOccured))
            val responseBody: ResponseBody = body.toResponseBody(APPLICATION_JSON)

            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(500)
                .message(StringTokens.UnknownErrorOccured)
                .body(responseBody)
                .build()
        }

    }
}