package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ElectricityBandResponse
import dev.gbenga.inaread.domain.repository.MeterCategoryRepository
import dev.gbenga.inaread.domain.services.MeterCategoryApiService

class MeterCategoryRepositoryImpl(private val meterCategoryApiService: MeterCategoryApiService) : MeterCategoryRepository,
NetworkRepository(){


    override suspend fun getMeterCategories(): RepoResult<List<ElectricityBandResponse>> = safeCall {
        meterCategoryApiService.getMeterCategory()
    }
}