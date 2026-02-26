package dev.gbenga.inaread.domain.repository

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ConsumptionRecordResponse
import dev.gbenga.inaread.data.model.PowerUsageRequest
import dev.gbenga.inaread.data.model.PowerUsageResponse
import dev.gbenga.inaread.data.model.PowerUsageSummaryResponse
import dev.gbenga.inaread.data.model.YearlyUsageResponse

interface MeterUsageRepository {

    suspend  fun getMeterSummaryForDay(dateYMD: String): RepoResult<List<PowerUsageSummaryResponse>>

    suspend fun executeAddNewReading(request: PowerUsageRequest): RepoResult<ConsumptionRecordResponse>

    suspend fun executeGetMonthlyUsage(): RepoResult<List<PowerUsageResponse>>

    suspend fun executeGetYearlyUsage(year: Int): RepoResult<List<YearlyUsageResponse>>

    suspend fun executeGetMeterTypes(): RepoResult<List<String>>

    suspend fun wipePowerUsageTable()
}