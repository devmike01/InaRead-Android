package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.domain.datastore.UserDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheckUserAuthenticationUseCase @Inject constructor(private val userDataStore: UserDataStore) {

    operator fun invoke() =  userDataStore.getProfileId().map { customerId ->
        customerId != null
    }
}