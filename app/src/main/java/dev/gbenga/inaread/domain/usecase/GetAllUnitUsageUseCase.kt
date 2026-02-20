package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.MonthlyUsage
import dev.gbenga.inaread.data.repository.AllUnitUsageRepository
import dev.gbenga.inaread.domain.repository.AppliancesRepository
import javax.inject.Inject

class GetAllUnitUsageUseCase @Inject constructor(val allUnitUsageRepository: AllUnitUsageRepository) {

    suspend operator fun invoke(): List<MonthlyUsage>{
        return allUnitUsageRepository.getAllMonthlyUsage()
            .map { (date, kilowatt, period, cost) -> val dayMonth = date.split("-") //Tue-12-Nov
                MonthlyUsage(
                    kilowatt = kilowatt,
                    period = period,
                    cost = cost,
                    day = dayMonth[0],
                    dayOfMonth = dayMonth[1],
                    month = dayMonth[2]
                ) }
    }
}