package dev.gbenga.inaread.domain.metrics

import dev.gbenga.inaread.data.model.ApplianceResponse
import javax.inject.Inject

class GetAppliancesUseCase @Inject constructor(val metricsRepository: MetricsRepository) {

    suspend operator fun invoke(): List<ApplianceResponse>{
        return metricsRepository.getAppliances()
    }
}