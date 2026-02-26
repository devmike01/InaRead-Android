package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.YearlyUsageResponse
import dev.gbenga.inaread.domain.repository.MeterUsageRepository
import javax.inject.Inject

class GetYearlyUsageUseCase @Inject constructor(private val meterUsageRepository: MeterUsageRepository) {

    suspend operator fun invoke(year: Int): RepoResult<List<YearlyUsageResponse>>{
        return meterUsageRepository.executeGetYearlyUsage(year)
    }
}