package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.model.ProfileResponse
import retrofit2.http.GET

interface ProfileApiService {

    @GET("/profile")
    suspend fun getProfile(): ProfileResponse

}