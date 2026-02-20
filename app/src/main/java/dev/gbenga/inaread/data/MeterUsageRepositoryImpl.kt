package dev.gbenga.inaread.data

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ConsumptionRecordResponse
import dev.gbenga.inaread.data.model.MeterResponse
import dev.gbenga.inaread.data.model.PowerUsageRequest
import dev.gbenga.inaread.data.model.PowerUsageResponse
import dev.gbenga.inaread.data.model.PowerUsageSummaryResponse
import dev.gbenga.inaread.data.network.MeterUsageStatisticService
import dev.gbenga.inaread.data.repository.NetworkRepository
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.domain.repository.MeterUsageRepository
import dev.gbenga.inaread.domain.services.MeterSummaryApiService
import dev.gbenga.inaread.utils.UserNotFoundException
import dev.gbenga.inaread.utils.UserProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.single

class MeterUsageRepositoryImpl (
    private val meterUsageApiService: MeterUsageStatisticService,
    private val userProvider: UserProvider
) : MeterUsageRepository, NetworkRepository() {


    override suspend fun getMeterSummaryForDay(
        dateYMD: String
    ): RepoResult<PowerUsageSummaryResponse> = safeCall {
        meterUsageApiService
            .getSummary(userProvider.getCustomerId(), dateYMD)
    }

    override suspend fun executeAddNewReading(request: PowerUsageRequest)
    : RepoResult<ConsumptionRecordResponse> = safeCall {
        meterUsageApiService.setNewReading(request)
    }

    override suspend fun executeGetUsageByUser()
    : RepoResult<List<PowerUsageResponse>> = safeCall  {
        meterUsageApiService.getAllUsage(userProvider.getCustomerId())
    }

    override suspend fun executeGetMeterTypes()
    : RepoResult<List<String>> = safeCall {
        meterUsageApiService.getMeterTypes()
    }


}