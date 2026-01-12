package dev.gbenga.inaread.domain.usescases.meter

import dev.gbenga.inaread.ui.home.ResIdIconItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import javax.inject.Inject

class MeterSummaryUseCase @Inject constructor(private val meterSummaryRepository: MeterSummaryRepository) {

    fun invoke(): Flow<Result<List<ResIdIconItem>>>{
        return flow {
            // No need for emit. it happens automatically
            val meterSummary = meterSummaryRepository.getMeterSummary()
            val (kilowatts, periodInDays, minorCost) = meterSummary.lastUsage
            val powerCostInNaira = BigDecimal(minorCost).divide(100.toBigDecimal())

            listOf(
                ResIdIconItem(
                    reading = kilowatts.toString(),
                    label = "Usage(kW)", // kilowatts per period
                    color = 0xFF42A5F5
                ),
                ResIdIconItem(
                    reading = periodInDays.toString(),
                    label = "Period(days)", // kilowatts per month
                    color = 0xFF42A5F5
                ),
                ResIdIconItem(
                    reading = powerCostInNaira.toPlainString(),
                    label = "Cost(Naira)", // kilowatts per month
                    color = 0xFF42A5F5
                )
            )
        }
    }
}