package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.ApplianceRequest
import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.domain.repository.MetricsRepository

class SetApplianceUseCase (private val metricsRepository: MetricsRepository) {

    suspend operator fun invoke(applianceRequest: ApplianceRequest): ApplianceResponse{
        return metricsRepository.setAppliance(applianceRequest)
    }
}