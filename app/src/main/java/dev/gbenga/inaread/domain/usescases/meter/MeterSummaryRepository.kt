package dev.gbenga.inaread.domain.usescases.meter

import dev.gbenga.inaread.data.model.MeterResponse

interface MeterSummaryRepository {

    suspend fun getMeterSummary(): MeterResponse

}