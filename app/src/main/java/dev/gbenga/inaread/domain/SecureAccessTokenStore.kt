package dev.gbenga.inaread.domain

interface SecureAccessTokenStore {
    fun setAccessToken(jwt: String)
    fun getAccessToken(): String?
}