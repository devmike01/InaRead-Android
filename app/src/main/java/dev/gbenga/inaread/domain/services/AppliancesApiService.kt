package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.data.model.ApplianceResponseData
import dev.gbenga.inaread.data.model.AppliancesRequest
import dev.gbenga.inaread.data.network.EndPoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppliancesApiService {

    @GET(EndPoints.GetAppliances.plus("/{userId}"))
    suspend fun getAppliances(@Path("userId") userId: String): Response<ApiResult<ApplianceResponse>>

    @POST(EndPoints.SetAppliance)
    suspend fun addAppliances(@Body request: AppliancesRequest): Response<ApiResult<ApplianceResponseData>>

}