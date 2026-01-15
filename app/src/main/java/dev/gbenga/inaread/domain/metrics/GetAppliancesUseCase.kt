package dev.gbenga.inaread.domain.metrics

import dev.gbenga.inaread.data.model.ApplianceResponse

class GetAppliancesUseCase(val metricsRepository: MetricsRepository) {

    suspend fun invoke(): List<ApplianceResponse>{
        return metricsRepository.getAppliances()
    }
}