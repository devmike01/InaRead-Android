package dev.gbenga.inaread.domain.allunits

import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.data.model.MonthlyUsageResponse

interface AllUnitUsageApiService {

    suspend fun getAllMonthlyUsage(userId: String): List<MonthlyUsageResponse>

    suspend fun uploadMonthlyUsage(userId: String, monthlyUsageRequest: MonthlyUsageRequest): MonthlyUsageResponse
}