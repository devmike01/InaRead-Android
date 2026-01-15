package dev.gbenga.inaread.data

import dev.gbenga.inaread.data.model.MeterResponse
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.domain.meter.MeterSummaryRepository
import dev.gbenga.inaread.domain.services.MeterSummaryApiService
import dev.gbenga.inaread.utils.UserNotFoundException
import kotlinx.coroutines.flow.firstOrNull

class MeterSummaryRepositoryImpl (
    private val meterSummaryApiService: MeterSummaryApiService,
    private val userDataStore: UserDataStore,
) : MeterSummaryRepository {

    override suspend fun getMeterSummaryForDay(): MeterResponse {
        val profileId = userDataStore.getProfileId().firstOrNull()
        return profileId?.let {
            meterSummaryApiService.fetchMeterSummary(profileId)
        } ?: throw UserNotFoundException()
    }


}