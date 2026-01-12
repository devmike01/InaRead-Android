package dev.gbenga.inaread.data.service

import dev.gbenga.inaread.data.model.MeterResponse

interface MeterSummaryApiService {

    suspend fun fetchMeterSummary(userId: String): MeterResponse

}