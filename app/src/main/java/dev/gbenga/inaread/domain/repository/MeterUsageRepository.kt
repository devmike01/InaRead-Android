package dev.gbenga.inaread.domain.repository

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ConsumptionRecordResponse
import dev.gbenga.inaread.data.model.PowerUsageRequest
import dev.gbenga.inaread.data.model.PowerUsageResponse
import dev.gbenga.inaread.data.model.PowerUsageSummaryResponse

interface MeterUsageRepository {

    suspend  fun getMeterSummaryForDay(dateYMD: String): RepoResult<PowerUsageSummaryResponse>

    suspend fun executeAddNewReading(request: PowerUsageRequest): RepoResult<ConsumptionRecordResponse>

    suspend fun executeGetUsageByUser(): RepoResult<List<PowerUsageResponse>>

    suspend fun executeGetMeterTypes(): RepoResult<List<String>>
}