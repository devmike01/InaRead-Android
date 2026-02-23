package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.domain.repository.AuthRepository
import javax.inject.Inject


class SignOutUseCase @Inject constructor(private val authRepo: AuthRepository) {

    suspend operator fun invoke(): RepoResult<String>{
        return authRepo.signOut()
    }
}