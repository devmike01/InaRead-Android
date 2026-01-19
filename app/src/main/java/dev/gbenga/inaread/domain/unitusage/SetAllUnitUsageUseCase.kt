package dev.gbenga.inaread.domain.unitusage

import dev.gbenga.inaread.data.model.MonthlyUsage
import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.domain.allunits.AllUnitUsageRepository
import javax.inject.Inject

class SetAllUnitUsageUseCase  @Inject constructor(val allUnitUsageRepository: AllUnitUsageRepository) {

    suspend operator fun invoke(monthlyUsageResponse: MonthlyUsageRequest): MonthlyUsage{
        return allUnitUsageRepository.uploadMonthlyUsage(monthlyUsageResponse).let { (date,
            kilowatt, period, cost) ->
            val dayMonth = date.split("-") //Tue-12-Nov
            MonthlyUsage(
                kilowatt = kilowatt,
                period = period,
                cost = cost,
                day = dayMonth[0],
                dayOfMonth = dayMonth[1],
                month = dayMonth[2]
            )
        }
    }
}