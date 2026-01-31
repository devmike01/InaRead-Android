package dev.gbenga.inaread.ui.usage

import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.data.model.MonthlyUsageResponse
import dev.gbenga.inaread.domain.services.AllUnitUsageApiService
import dev.gbenga.inaread.domain.repository.AllUnitUsageRepository
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.utils.UserNotFoundException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineDispatcher

class AllUnitUsageRepositoryImpl(private val valUnitUsageApiService: AllUnitUsageApiService,
    private val userDataStore: UserDataStore,
    private val io: CoroutineDispatcher) : AllUnitUsageRepository {

    override suspend fun getAllMonthlyUsage(): List<MonthlyUsageResponse> {
        return withContext(io){
            val userId = userDataStore.getProfileId().firstOrNull() ?: throw UserNotFoundException()
            valUnitUsageApiService.getAllMonthlyUsage(userId)
        }
    }

    override suspend fun uploadMonthlyUsage(monthlyUsageResponse: MonthlyUsageRequest): MonthlyUsageResponse {
        return withContext(io){
            val userId = userDataStore.getProfileId().firstOrNull() ?: throw UserNotFoundException()
            valUnitUsageApiService.uploadMonthlyUsage(userId, monthlyUsageResponse)
        }
    }
}