package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.domain.repository.AuthRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheckUserAuthenticationUseCase @Inject constructor(private val authRepo: AuthRepository) {

    suspend operator fun invoke() =  authRepo.isSignedIn()
}