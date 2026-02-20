package dev.gbenga.inaread.ui.usage

import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.data.model.MonthlyUsageResponse
import dev.gbenga.inaread.data.repository.AllUnitUsageRepository
import dev.gbenga.inaread.domain.services.AllUnitUsageApiService
import dev.gbenga.inaread.utils.UserProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AllUnitUsageRepositoryImpl(private val valUnitUsageApiService: AllUnitUsageApiService,
    private val userDataStore: UserProvider,
    private val io: CoroutineDispatcher) : AllUnitUsageRepository {

    override suspend fun getAllMonthlyUsage(): List<MonthlyUsageResponse> {
        return withContext(io){
            val userId = userDataStore.getCustomerId()
            valUnitUsageApiService.getAllMonthlyUsage(userId)
        }
    }

    override suspend fun uploadMonthlyUsage(monthlyUsageResponse: MonthlyUsageRequest): MonthlyUsageResponse {
        return withContext(io){
            val userId = userDataStore.getCustomerId()
            valUnitUsageApiService.uploadMonthlyUsage(userId, monthlyUsageResponse)
        }
    }
}