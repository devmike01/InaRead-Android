package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.ConsumptionRecordResponse
import dev.gbenga.inaread.data.model.PowerUsageRequest
import dev.gbenga.inaread.data.model.PowerUsageResponse
import dev.gbenga.inaread.data.model.PowerUsageSummaryResponse
import dev.gbenga.inaread.data.model.YearlyUsageResponse
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

    @GET("${EndPoints.UsageMonthly}/{userId}")
    suspend fun getSummaryOfMonthByDate(@Path("userId") userId: String,
                                        @Query("date") dateYMD: String)
    : Response<ApiResult<List<PowerUsageSummaryResponse>>>

    @GET("${EndPoints.UsageYearly}/{userId}")
    suspend fun getAllUsageForYear(@Path("userId") userId: String,
                                        @Query("year") year: Int)
            : Response<ApiResult<List<YearlyUsageResponse>>>
}