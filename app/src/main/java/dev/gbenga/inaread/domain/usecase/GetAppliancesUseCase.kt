package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.Appliance
import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.data.model.toAppliance
import dev.gbenga.inaread.domain.repository.MetricsRepository
import javax.inject.Inject

class GetAppliancesUseCase @Inject constructor(val metricsRepository: MetricsRepository) {

    suspend operator fun invoke(): List<Appliance>{
        return metricsRepository.getAppliances()
            .map { a -> a.toAppliance() }
    }
}