package dev.gbenga.inaread.data.network

import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.ConsumptionRecordResponse
import dev.gbenga.inaread.data.model.PowerUsageRequest
import dev.gbenga.inaread.data.model.PowerUsageResponse
import dev.gbenga.inaread.data.model.PowerUsageSummaryResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response

interface MeterUsageStatisticService {

    @GET("${EndPoints.GetAllUsages}/{userId}")
    fun getAllUsage(@Path("userId") userId: String): Response<ApiResult<List<PowerUsageResponse>>>

    @POST(EndPoints.RecordNewUsage)
    fun setNewReading(request: PowerUsageRequest): Response<ApiResult<ConsumptionRecordResponse>>

    @GET(EndPoints.MeterType)
    fun getMeterTypes(): Response<ApiResult<List<String>>>

    // usage/c867773d0beb4bc1877209acd959da57?date=2026-01-02

    @GET("${EndPoints.Usage}/{userId}")
    suspend fun getSummary(@Path("userId") userId: String,
                   @Query("date") dateYMD: String)
    : Response<ApiResult<List<PowerUsageSummaryResponse>>>
}