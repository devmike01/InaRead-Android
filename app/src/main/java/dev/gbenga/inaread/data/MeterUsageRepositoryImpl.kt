package dev.gbenga.inaread.data

import android.util.Log
import dev.gbenga.inaread.data.db.PowerUsageSummaryDao
import dev.gbenga.inaread.data.db.entities.PowerUsageSummaryEntity
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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.withContext

class MeterUsageRepositoryImpl (
    private val meterUsageApiService: MeterUsageStatisticService,
    private val userProvider: UserProvider,
    private val powerUsageSummaryDao: PowerUsageSummaryDao,
    private val ioContext: CoroutineDispatcher = Dispatchers.IO
) : MeterUsageRepository, NetworkRepository() {


    override suspend fun getMeterSummaryForDay(
        dateYMD: String
    ): RepoResult<List<PowerUsageSummaryResponse>>{
       return withContext(ioContext){
           powerUsageSummaryDao
               .getSummaryByFromDate(dateYMD)
               ?.let { entity ->
                   RepoResult.Success(listOf(
                       PowerUsageSummaryResponse(
                           periodInDays = entity.periodInDays,
                           costPerKwh = entity.costPerKwh,
                           meterType = entity.meterType,
                           totalMonthPowerUsage = entity.totalMonthPowerUsage,
                           fromDate = entity.fromDate,
                           toDate = entity.toDate,
                           totalSpent = entity.totalSpent
                       )
                   ))
               }
       } ?: safeCall {
                meterUsageApiService
                    .getSummary(dateYMD = dateYMD,
                        userId = userProvider.getCustomerId())
            }.apply {
                if (this is RepoResult.Success){
                    val resData = data.firstOrNull()
                    resData?.let {
                        withContext(ioContext){
                            powerUsageSummaryDao.insert(PowerUsageSummaryEntity(
                                fromDate = resData.fromDate,
                                periodInDays = resData.periodInDays,
                                costPerKwh = resData.costPerKwh,
                                meterType = resData.meterType,
                                totalMonthPowerUsage = resData.totalMonthPowerUsage,
                                toDate = resData.toDate,
                                totalSpent = resData.totalSpent,

                            ))
                        }
                    }

                }
            }


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

    override suspend fun wipePowerUsageTable() {
        withContext(ioContext){
            powerUsageSummaryDao.wipeTable()
        }
    }


}