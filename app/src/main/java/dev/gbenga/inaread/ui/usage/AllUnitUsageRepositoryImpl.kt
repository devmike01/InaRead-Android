package dev.gbenga.inaread.ui.usage

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.PowerUsageResponse
import dev.gbenga.inaread.data.repository.AllUnitUsageRepository
import dev.gbenga.inaread.data.repository.NetworkRepository
import dev.gbenga.inaread.domain.services.AllUsageApiService
import dev.gbenga.inaread.utils.UserProvider
import kotlinx.coroutines.CoroutineDispatcher

class AllUnitUsageRepositoryImpl(private val allUsageApiService: AllUsageApiService,
                                 private val userDataStore: UserProvider,
                                 private val io: CoroutineDispatcher) : AllUnitUsageRepository,
    NetworkRepository() {

    override suspend fun getAllMonthlyUsage(): RepoResult<List<PowerUsageResponse>> = safeCall {
        allUsageApiService.getAllPowerUsages(userDataStore.getCustomerId())
    }

}