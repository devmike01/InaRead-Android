package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.data.model.MonthlyUsageResponse
import dev.gbenga.inaread.data.model.PowerUsageResponse

interface AllUnitUsageRepository {

    suspend fun getAllMonthlyUsage(): RepoResult<List<PowerUsageResponse>>

   // suspend fun uploadMonthlyUsage(monthlyUsageResponse: MonthlyUsageRequest): MonthlyUsageResponse

}