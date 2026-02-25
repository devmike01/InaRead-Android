package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.Appliance
import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.data.model.toAppliance
import dev.gbenga.inaread.domain.repository.AppliancesRepository
import dev.gbenga.inaread.domain.services.AppliancesApiService
import javax.inject.Inject

class GetAppliancesUseCase @Inject constructor(private val applianceRepository: AppliancesRepository) {

    suspend operator fun invoke(): RepoResult<List<Appliance>>{
        return applianceRepository
            .executeGetAppliances()
    }
}