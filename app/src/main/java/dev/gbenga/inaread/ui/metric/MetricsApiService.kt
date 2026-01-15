package dev.gbenga.inaread.ui.metric

import dev.gbenga.inaread.data.model.ApplianceRequest
import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.data.model.MonthChartResponse

interface MetricsApiService {

    suspend fun getAppliances(userId: String): List<ApplianceResponse>

    suspend fun setAppliance(userId: String, applianceRequest: ApplianceRequest): ApplianceResponse

    suspend fun getMonthChart(userId: String): List<MonthChartResponse>
}