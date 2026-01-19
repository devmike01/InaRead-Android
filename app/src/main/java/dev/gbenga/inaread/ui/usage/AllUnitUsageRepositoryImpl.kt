package dev.gbenga.inaread.ui.usage

import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.data.model.MonthlyUsageResponse
import dev.gbenga.inaread.domain.allunits.AllUnitUsageApiService
import dev.gbenga.inaread.domain.allunits.AllUnitUsageRepository
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.utils.UserNotFoundException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class AllUnitUsageRepositoryImpl(private val valUnitUsageApiService: AllUnitUsageApiService,
    private val userDataStore: UserDataStore,
    private val io: CoroutineContext) : AllUnitUsageRepository {

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