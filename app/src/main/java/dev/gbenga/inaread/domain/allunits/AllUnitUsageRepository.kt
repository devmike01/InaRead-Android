package dev.gbenga.inaread.domain.allunits

import dev.gbenga.inaread.data.model.MonthlyUsageResponse
import dev.gbenga.inaread.data.model.MonthValue
import dev.gbenga.inaread.data.model.MonthlyUsageRequest

interface AllUnitUsageRepository {

    suspend fun getAllMonthlyUsage(): List<MonthlyUsageResponse>

    suspend fun uploadMonthlyUsage(monthlyUsageResponse: MonthlyUsageRequest): MonthlyUsageResponse
}