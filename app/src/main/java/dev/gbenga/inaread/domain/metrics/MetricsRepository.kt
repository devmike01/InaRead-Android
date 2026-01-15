package dev.gbenga.inaread.domain.metrics

import dev.gbenga.inaread.data.model.ApplianceRequest
import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.data.model.MonthChartResponse

interface MetricsRepository {

    suspend fun getAppliances(): List<ApplianceResponse>

    suspend fun setAppliance(applianceRequest: ApplianceRequest): ApplianceResponse

    suspend fun getMonthChart(): List<MonthChartResponse>
}