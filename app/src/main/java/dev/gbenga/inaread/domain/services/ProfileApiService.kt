package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.network.EndPoints
import retrofit2.Response
import retrofit2.http.GET

@Deprecated("profile should use local db")
interface ProfileApiService {

    @GET(EndPoints.Profile)
    suspend fun getProfile(): Response<ApiResult<LoginResponse>>

}