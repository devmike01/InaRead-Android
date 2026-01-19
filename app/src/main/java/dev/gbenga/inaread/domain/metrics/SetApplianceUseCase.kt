package dev.gbenga.inaread.domain.metrics

import dev.gbenga.inaread.data.model.ApplianceRequest
import dev.gbenga.inaread.data.model.ApplianceResponse

class SetApplianceUseCase (private val metricsRepository: MetricsRepository) {

    suspend operator fun invoke(applianceRequest: ApplianceRequest): ApplianceResponse{
        return metricsRepository.setAppliance(applianceRequest)
    }
}