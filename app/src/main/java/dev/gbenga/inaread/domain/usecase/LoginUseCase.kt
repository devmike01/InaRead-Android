package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.LoginInput
import dev.gbenga.inaread.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(username: String, password: String) = authRepository.authenticate(
        LoginInput(username = username,
            password = password))
}