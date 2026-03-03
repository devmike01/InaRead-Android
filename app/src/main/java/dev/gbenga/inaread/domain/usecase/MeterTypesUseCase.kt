package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.domain.repository.AuthRepository
import javax.inject.Inject

class MeterTypesUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): RepoResult<List<String>>{
        return authRepository.getMeterTypes()
    }
}