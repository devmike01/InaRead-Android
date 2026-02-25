package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.PowerUsageResponse
import dev.gbenga.inaread.data.repository.AllUnitUsageRepository
import javax.inject.Inject

class GetAllUnitUsageUseCase @Inject constructor(val allUnitUsageRepository: AllUnitUsageRepository) {

    suspend operator fun invoke(): RepoResult<List<PowerUsageResponse>>{
        return allUnitUsageRepository
            .getAllMonthlyUsage()
    }
}