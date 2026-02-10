package dev.gbenga.inaread.adapters

import dev.gbenga.inaread.domain.SecureAccessTokenStore
import okhttp3.Interceptor
import okhttp3.Response

class NetworkResponseInterceptor(
    private val accessTokenStore: SecureAccessTokenStore,
    ) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = accessTokenStore.getAccessToken()

        val newRequest = if (!token.isNullOrBlank()){
            originalRequest.newBuilder()
                .addHeader("Authorization", token)
                .build()
        }else{
            originalRequest
        }

        return chain.proceed(newRequest)
    }
}