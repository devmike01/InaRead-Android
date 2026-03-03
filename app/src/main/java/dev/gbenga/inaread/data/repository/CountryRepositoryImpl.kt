package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.CountryResponse
import dev.gbenga.inaread.domain.repository.CountryRepository
import dev.gbenga.inaread.domain.services.CountryApiService

class CountryRepositoryImpl(private val countryApiService: CountryApiService) : CountryRepository, NetworkRepository() {

    override suspend fun getCountries(): RepoResult<List<CountryResponse>> = safeCall {
        countryApiService.getCountries()
    }
}