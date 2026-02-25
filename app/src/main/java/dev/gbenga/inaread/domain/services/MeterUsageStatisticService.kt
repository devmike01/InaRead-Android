package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.ConsumptionRecordResponse
import dev.gbenga.inaread.data.model.PowerUsageRequest
import dev.gbenga.inaread.data.model.PowerUsageResponse
import dev.gbenga.inaread.data.model.PowerUsageSummaryResponse
import dev.gbenga.inaread.data.network.EndPoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MeterUsageStatisticService {

    @GET("${EndPoints.GetAllUsages}/{userId}")
    suspend fun getAllUsage(@Path("userId") userId: String): Response<ApiResult<List<PowerUsageResponse>>>

    @POST(EndPoints.RecordNewUsage)
    suspend fun setNewReading(@Body request: PowerUsageRequest): Response<ApiResult<ConsumptionRecordResponse>>

    @GET(EndPoints.MeterType)
    suspend fun getMeterTypes(): Response<ApiResult<List<String>>>

    // usage/c867773d0beb4bc1877209acd959da57?date=2026-01-02

    @GET("${EndPoints.Usage}/{userId}")
    suspend fun getSummary(@Path("userId") userId: String,
                           @Query("date") dateYMD: String)
    : Response<ApiResult<List<PowerUsageSummaryResponse>>>
}