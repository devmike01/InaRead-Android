package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.MonthlyUsage
import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.domain.repository.AppliancesRepository
import javax.inject.Inject

class SetAllUnitUsageUseCase  @Inject constructor(val allUnitUsageRepository: AppliancesRepository) {

//    suspend operator fun invoke(monthlyUsageResponse: MonthlyUsageRequest): MonthlyUsage{
//        return MonthlyUsage()
//    }
}