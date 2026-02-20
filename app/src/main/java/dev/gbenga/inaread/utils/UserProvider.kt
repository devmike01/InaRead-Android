package dev.gbenga.inaread.utils

import dev.gbenga.inaread.domain.datastore.UserDataStore
import kotlinx.coroutines.flow.single

interface UserProvider {

    @Throws(UserNotFoundException::class)
    suspend fun getCustomerId(): String
}

class UserProviderImpl( private val userDataStore: UserDataStore,) : UserProvider{

    override suspend fun getCustomerId(): String = getOrThrowUserNotAuth {
        userDataStore.getProfileId().single()
    }

}


@Throws(UserNotFoundException::class)
suspend fun getOrThrowUserNotAuth(block: suspend () -> String?): String{
    return block() ?: throw UserNotFoundException()
}

//  private val userDataStore: UserDataStore,