package dev.gbenga.inaread.domain.repository

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.CountryResponse

interface CountryRepository {

    suspend fun getCountries(): RepoResult<List<CountryResponse>>
}