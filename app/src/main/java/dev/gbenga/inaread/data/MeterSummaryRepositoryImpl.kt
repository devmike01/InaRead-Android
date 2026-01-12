package dev.gbenga.inaread.data

import dev.gbenga.inaread.data.model.MeterResponse
import dev.gbenga.inaread.data.service.MeterSummaryApiService
import dev.gbenga.inaread.domain.usescases.meter.MeterSummaryRepository

class MeterSummaryRepositoryImpl (
    private val meterSummaryApiService: MeterSummaryApiService,

) : MeterSummaryRepository {

    override suspend fun getMeterSummary(): MeterResponse {
        return meterSummaryApiService.fetchMeterSummary()
    }


}