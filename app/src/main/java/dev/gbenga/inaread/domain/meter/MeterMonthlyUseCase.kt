package dev.gbenga.inaread.domain.meter

import dev.gbenga.inaread.data.model.MonthlyMeterUsage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MeterMonthlyUseCase @Inject constructor(private val meterSummaryRepository: MeterSummaryRepository) {

    suspend fun invoke(dayOfMonth: Int): Flow<Result<MonthlyMeterUsage>>{
        return flowOf (

            runCatching {
                val meterSummary = meterSummaryRepository.getMeterSummaryForDay()
                meterSummary.monthlyMeterUsage
            }
        )
    }

}