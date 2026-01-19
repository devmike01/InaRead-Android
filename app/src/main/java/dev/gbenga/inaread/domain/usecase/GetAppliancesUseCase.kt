package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.domain.repository.MetricsRepository
import javax.inject.Inject

class GetAppliancesUseCase @Inject constructor(val metricsRepository: MetricsRepository) {

    suspend operator fun invoke(): List<ApplianceResponse>{
        return metricsRepository.getAppliances()
    }
}