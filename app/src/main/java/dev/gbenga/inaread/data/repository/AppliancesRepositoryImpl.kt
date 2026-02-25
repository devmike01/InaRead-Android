package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.Appliance
import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.data.model.ApplianceResponseData
import dev.gbenga.inaread.data.model.AppliancesRequest
import dev.gbenga.inaread.domain.repository.AppliancesRepository
import dev.gbenga.inaread.domain.services.AppliancesApiService
import dev.gbenga.inaread.utils.UserProvider

class AppliancesRepositoryImpl(
    private val appliancesApiService: AppliancesApiService,
    private val userProvider: UserProvider) : AppliancesRepository, NetworkRepository() {

    override suspend fun executeGetAppliances(): RepoResult<List<Appliance>> = safeCall {
        val customerId = userProvider.getCustomerId()
        appliancesApiService.getAppliances(customerId)
    }

    override suspend fun executeAddAppliance(request: AppliancesRequest): RepoResult<ApplianceResponseData> = safeCall {
        appliancesApiService.addAppliances(request)
    }
}