package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.data.model.MonthlyUsageResponse

interface AllUnitUsageRepository {

    suspend fun getAllMonthlyUsage(): List<MonthlyUsageResponse>

    suspend fun uploadMonthlyUsage(monthlyUsageResponse: MonthlyUsageRequest): MonthlyUsageResponse

}