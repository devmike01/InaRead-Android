package dev.gbenga.inaread.domain.meter

import dev.gbenga.inaread.data.model.LastPowerUsage
import dev.gbenga.inaread.data.model.MeterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MeterSummaryUseCase @Inject constructor(private val meterSummaryRepository: MeterSummaryRepository) {

    suspend operator fun invoke(): Flow<Result<MeterResponse>>{
        return flowOf (

            runCatching {
                 meterSummaryRepository.getMeterSummaryForDay()
            }
            )
    }
}