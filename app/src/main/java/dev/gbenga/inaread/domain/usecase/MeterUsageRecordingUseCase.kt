package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ConsumptionRecordResponse
import dev.gbenga.inaread.data.model.PowerUsageRequest
import dev.gbenga.inaread.domain.repository.MeterUsageRepository
import javax.inject.Inject

class MeterUsageRecordingUseCase @Inject constructor(
    private val repository: MeterUsageRepository) {

    suspend operator fun invoke(request: PowerUsageRequest): RepoResult<ConsumptionRecordResponse>{
        return repository.executeAddNewReading(request)
    }
}