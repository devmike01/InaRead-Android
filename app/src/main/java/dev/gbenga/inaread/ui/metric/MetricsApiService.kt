package dev.gbenga.inaread.ui.metric

import dev.gbenga.inaread.data.model.ApplianceRequest
import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.data.model.MonthChartResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MetricsApiService {

    @GET("/appliances/{user_id}")
    suspend fun getAppliances(@Path("user_id") userId: String): List<ApplianceResponse>

    @POST("/appliances/new")
    suspend fun setAppliance(@Body  applianceRequest: ApplianceRequest): ApplianceResponse

    @GET("/appliances/{user_id}")
    suspend fun getMonthChart(@Path("user_id") userId: String): List<MonthChartResponse>
}