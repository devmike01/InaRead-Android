package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.MonthlyMeterUsage
import dev.gbenga.inaread.domain.repository.MeterSummaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MeterMonthlyUseCase @Inject constructor(private val meterSummaryRepository: MeterSummaryRepository) {

    suspend operator fun invoke(dayOfMonth: Int): Flow<Result<MonthlyMeterUsage>>{
        return flowOf (

            runCatching {
                val meterSummary = meterSummaryRepository.getMeterSummaryForDay()
                meterSummary.monthlyMeterUsage
            }
        )
    }

}