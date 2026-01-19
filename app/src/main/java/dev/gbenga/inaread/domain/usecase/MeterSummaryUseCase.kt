package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.MeterResponse
import dev.gbenga.inaread.domain.repository.MeterSummaryRepository
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