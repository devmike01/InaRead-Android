package dev.gbenga.inaread.domain.metrics

import dev.gbenga.inaread.data.model.MonthChartResponse
import javax.inject.Inject

class GetMonthlyChartUseCase @Inject constructor(private val metricsRepository: MetricsRepository) {

    suspend fun invoke(): List<MonthChartResponse>{
        return metricsRepository.getMonthChart()
    }

}