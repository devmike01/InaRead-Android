package dev.gbenga.inaread.domain.repository

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.data.model.ApplianceResponseData
import dev.gbenga.inaread.data.model.AppliancesRequest
import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.data.model.MonthlyUsageResponse

interface AppliancesRepository {

    suspend fun executeGetAppliances(): RepoResult<ApplianceResponse>

    suspend fun executeAddAppliance(request: AppliancesRequest): RepoResult<ApplianceResponseData>
}