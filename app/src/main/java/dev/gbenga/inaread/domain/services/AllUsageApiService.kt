package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.PowerUsageResponse
import dev.gbenga.inaread.data.network.EndPoints
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AllUsageApiService {

    // http://127.0.0.1:8080/api/v1/usage/all/c867773d0beb4bc1877209acd959da57
    @GET("${EndPoints.AllPowerUsage}/{userId}")
    suspend fun getAllPowerUsages(@Path("userId") userId: String): Response<ApiResult<List<PowerUsageResponse>>>
}