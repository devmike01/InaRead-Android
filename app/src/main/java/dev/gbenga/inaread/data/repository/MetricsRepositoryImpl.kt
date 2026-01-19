package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.model.ApplianceRequest
import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.data.model.MonthChartResponse
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.domain.repository.MetricsRepository
import dev.gbenga.inaread.ui.metric.MetricsApiService
import dev.gbenga.inaread.utils.UserNotFoundException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MetricsRepositoryImpl(
    private val ioContext: CoroutineContext,
    private val userDataStore: UserDataStore,
    private val metricsApiService: MetricsApiService
): MetricsRepository {

    override suspend fun getAppliances(): List<ApplianceResponse> = useUserIdInIO { userId ->
        metricsApiService.getAppliances(userId)
    }

    override suspend fun setAppliance(applianceRequest: ApplianceRequest) = useUserIdInIO { userId ->
        metricsApiService.setAppliance(userId, applianceRequest)
    }

    override suspend fun getMonthChart(): List<MonthChartResponse> = useUserIdInIO { userId ->
        metricsApiService.getMonthChart(userId)
    }

    private suspend fun <T> useUserIdInIO( block: suspend (String) -> T): T{
        val userId = userDataStore.getProfileId().firstOrNull() ?: throw UserNotFoundException()
        return withContext(ioContext) {
            block(userId)
        }
    }

}