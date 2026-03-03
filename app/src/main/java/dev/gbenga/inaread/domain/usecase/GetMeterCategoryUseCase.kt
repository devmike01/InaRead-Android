package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ElectricityBandResponse
import dev.gbenga.inaread.domain.repository.MeterCategoryRepository
import javax.inject.Inject

class GetMeterCategoryUseCase @Inject constructor(private val meterCategoryRepository: MeterCategoryRepository) {

    suspend operator fun invoke(): RepoResult<List<ElectricityBandResponse>> {
        return meterCategoryRepository.getMeterCategories()
    }
}