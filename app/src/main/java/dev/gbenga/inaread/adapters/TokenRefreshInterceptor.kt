package dev.gbenga.inaread.adapters

import android.security.keystore.UserNotAuthenticatedException
import com.google.gson.Gson
import dev.gbenga.inaread.BuildConfig
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.RefreshTokenRequest
import dev.gbenga.inaread.domain.services.RefreshTokenApiService
import dev.gbenga.inaread.utils.UserProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class TokenRefreshInterceptor(
    private val refreshTokenApi: RefreshTokenApiService,
    private val userProvider: UserProvider,
    private val gson : Gson = Gson()): Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val contentSize = response.header("Content-Length")
        val contentBody = response.peekBody(contentSize?.toLong() ?: 500L)
        val apiResult = gson.fromJson(contentBody.string(), ApiResult::class.java)


        if (apiResult.error?.contains("JWT expired") == true){
            response.close()
            val newRequest = refreshToken(response)
            return newRequest?.let(chain::proceed)
                ?: Response.Builder()
                    .body(contentBody.string()
                        .toResponseBody("application/json".toMediaType()))
                .build()
        }
        return response
    }

    fun refreshToken(response: Response): Request?{
        if (responseCount(response) >= 2){
            return null
        }

        return runBlocking {
            try {

                val refreshToken = RefreshTokenRequest(
                    token = userProvider.getAccessToken(),
                    username = userProvider.getCurrentUsername()
                )
                val refreshTokenResponse = refreshTokenApi.refreshToken(refreshToken)

                if (refreshTokenResponse.isSuccess){
                    val refreshData = refreshTokenResponse.data ?: throw UserNotAuthenticatedException()
                    val refresh  = refreshData.refreshToken
                    val accessToken = refreshData.token
                    userProvider.setRefreshToken(refresh)
                    userProvider.setAccessToken(accessToken)
                }

                response.request.newBuilder()
                    .header("Authorization", "Bearer ${userProvider.getAccessToken()}")
                    .build()

            }catch (e: Exception){
                if (BuildConfig.DEBUG){
                    e.printStackTrace()
                }
                null
            }
        }

    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var priorResponse = response.priorResponse
        while (priorResponse != null){
            count++
            priorResponse = priorResponse.priorResponse
        }
        return count
    }

}