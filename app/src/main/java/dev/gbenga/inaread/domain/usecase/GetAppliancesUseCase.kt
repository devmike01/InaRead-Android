package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.Appliance
import dev.gbenga.inaread.data.model.toAppliance
import javax.inject.Inject

class GetAppliancesUseCase @Inject constructor() {

    suspend operator fun invoke(): List<Appliance>{
        return emptyList()
    }
}