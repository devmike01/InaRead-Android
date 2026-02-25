package dev.gbenga.inaread.adapters

import android.security.keystore.UserNotAuthenticatedException
import android.util.Log
import com.google.gson.Gson
import dev.gbenga.inaread.BuildConfig
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.RefreshTokenRequest
import dev.gbenga.inaread.domain.services.RefreshTokenApiService
import dev.gbenga.inaread.utils.UserProvider
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenRefreshAuthentication(
    private val refreshTokenApi: RefreshTokenApiService,
    private val userProvider: UserProvider,
    private val gson : Gson = Gson()) : Authenticator {

    val mutex = Mutex()


    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2){
            return null
        }

        val contentSize = response.header("Content-Length")
        val contentBody = response.peekBody(contentSize?.toLong() ?: 500L)
        val apiResult = gson.fromJson(contentBody.string(), ApiResult::class.java)

        return if (apiResult.error?.contains("JWT expired") == true){
            runBlocking {
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
        }else{
            response.request
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