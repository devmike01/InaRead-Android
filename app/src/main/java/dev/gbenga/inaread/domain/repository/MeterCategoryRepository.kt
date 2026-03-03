package dev.gbenga.inaread.domain.repository

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ElectricityBandResponse

interface MeterCategoryRepository {

    suspend fun getMeterCategories(): RepoResult<List<ElectricityBandResponse>>
}