package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.ElectricityBandResponse
import dev.gbenga.inaread.data.network.EndPoints
import retrofit2.Response
import retrofit2.http.GET

interface MeterCategoryApiService {

    @GET(EndPoints.MeterCategory)
    suspend fun getMeterCategory(): Response<ApiResult<List<ElectricityBandResponse>>>
}