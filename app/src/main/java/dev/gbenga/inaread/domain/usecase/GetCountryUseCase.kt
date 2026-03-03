package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.CountryResponse
import dev.gbenga.inaread.domain.repository.CountryRepository
import javax.inject.Inject

class GetCountryUseCase @Inject constructor(private val countryRepository: CountryRepository) {

    suspend operator fun invoke(): RepoResult<List<CountryResponse>>{
        return countryRepository.getCountries()
    }
}