package dev.gbenga.inaread.domain.meter

import dev.gbenga.inaread.data.model.MeterResponse

interface MeterSummaryRepository {

    suspend  fun getMeterSummaryForDay(): MeterResponse

}