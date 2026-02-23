package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.domain.repository.AuthRepository
import dev.gbenga.inaread.domain.repository.MeterUsageRepository
import javax.inject.Inject


class SignOutUseCase @Inject constructor(private val authRepo: AuthRepository,
    private val meterUsageRepo: MeterUsageRepository) {

    suspend operator fun invoke(): RepoResult<String>{
        meterUsageRepo.wipePowerUsageTable()
        return authRepo.signOut()
    }
}