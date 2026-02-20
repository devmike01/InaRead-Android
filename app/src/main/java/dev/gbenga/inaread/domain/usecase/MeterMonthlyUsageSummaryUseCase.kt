package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.PowerUsageSummaryResponse
import dev.gbenga.inaread.domain.repository.MeterUsageRepository
import javax.inject.Inject

class MeterMonthlyUsageSummaryUseCase @Inject constructor(private val meterUsageRepository: MeterUsageRepository) {

    // MonthlyMeterUsage
    suspend operator fun invoke(dateYMD: String): RepoResult<PowerUsageSummaryResponse>{
        return meterUsageRepository
            .getMeterSummaryForDay(dateYMD);
    }

}