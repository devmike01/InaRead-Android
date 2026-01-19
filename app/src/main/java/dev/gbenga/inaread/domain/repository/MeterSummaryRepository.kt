package dev.gbenga.inaread.domain.repository

import dev.gbenga.inaread.data.model.MeterResponse

interface MeterSummaryRepository {

    suspend  fun getMeterSummaryForDay(): MeterResponse

}