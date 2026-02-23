package dev.gbenga.inaread.domain

interface SecureAccessTokenStore {
    fun setAccessToken(jwt: String)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun setRefreshToken(token: String)
    fun removeTokens()
}