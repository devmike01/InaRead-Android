package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.MeterResponse
import dev.gbenga.inaread.data.model.PowerUsageSummaryResponse
import dev.gbenga.inaread.domain.repository.MeterUsageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MeterSummaryUseCase @Inject constructor(private val meterUsageRepository: MeterUsageRepository) {

    // MonthlyMeterUsage
    suspend operator fun invoke(dateYMD: String): RepoResult<List<PowerUsageSummaryResponse>>{
        return meterUsageRepository
            .getMeterSummaryForDay(dateYMD);
    }

}