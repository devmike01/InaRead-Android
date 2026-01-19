package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.MonthChartResponse
import dev.gbenga.inaread.domain.repository.MetricsRepository
import javax.inject.Inject

class GetMonthlyChartUseCase @Inject constructor(private val metricsRepository: MetricsRepository) {

    suspend operator fun invoke(): List<MonthChartResponse>{
        return metricsRepository.getMonthChart()
    }

}