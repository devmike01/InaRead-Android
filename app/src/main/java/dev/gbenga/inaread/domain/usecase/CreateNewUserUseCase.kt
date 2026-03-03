package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.NewCustomerResponse
import dev.gbenga.inaread.data.model.SignUpOutput
import dev.gbenga.inaread.domain.repository.AuthRepository
import javax.inject.Inject

class CreateNewUserUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(signUpRequest: SignUpRequest): RepoResult<NewCustomerResponse>{
        return authRepository.signUp(signUpRequest)
    }
}