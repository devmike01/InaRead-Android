package dev.gbenga.inaread.domain.metrics

import dev.gbenga.inaread.data.model.MonthChartResponse

class GetMonthlyChartUseCase (private val metricsRepository: MetricsRepository) {

    suspend fun invoke(): List<MonthChartResponse>{
        return metricsRepository.getMonthChart()
    }

}