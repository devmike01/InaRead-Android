package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.model.MeterResponse

interface MeterSummaryApiService {

    suspend fun fetchMeterSummary(userId: String): MeterResponse

}