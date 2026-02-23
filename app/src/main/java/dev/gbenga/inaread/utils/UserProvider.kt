package dev.gbenga.inaread.utils

import android.util.Log
import dev.gbenga.inaread.data.db.UserDao
import dev.gbenga.inaread.data.db.entities.UserEntity
import dev.gbenga.inaread.domain.SecureAccessTokenStore
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.domain.providers.CoroutineProviders
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

interface UserProvider {

    @Throws(UserNotFoundException::class)
    suspend fun getCustomerId(): String

    suspend fun setCustomerId(customerId: String)

    suspend fun getCurrentUser(): UserEntity

    suspend fun getCurrentUsername(): String

    @Throws(UserNotFoundException::class)
    suspend fun getAccessToken(): String

    suspend fun setAccessToken(access: String)

    suspend fun setRefreshToken(refreshToken: String)

    @Throws(UserNotFoundException::class)
    suspend fun getRefreshToken(): String

    suspend fun removeTokens()
}

class UserProviderImpl(
    private val userDataStore: UserDataStore,
    private val userDao: UserDao,
    private val accessTokenStore: SecureAccessTokenStore,
    private val io: CoroutineDispatcher = Dispatchers.IO
) : UserProvider{

    override suspend fun getCustomerId(): String = getOrThrowUserNotAuth {
        Log.d("ThrowUserNotAuth", "ThrowUserNotAuth: ${userDataStore.getProfileId().firstOrNull()}")
        userDataStore.getProfileId().firstOrNull()
    }

    override suspend fun setCustomerId(customerId: String) {
        userDataStore.setProfileId(customerId)
    }

    override suspend fun getCurrentUser(): UserEntity {
        return getOrThrowUserNotAuth { userDao.getProfile(getCustomerId()) }
    }

    override suspend fun getCurrentUsername(): String {
        return getCurrentUser().username
    }

    override suspend fun getAccessToken(): String {
        return accessTokenStore.getAccessToken() ?: throw UserNotFoundException()
    }

    override suspend fun setAccessToken(access: String) {
        accessTokenStore.setAccessToken(access)
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        accessTokenStore.setRefreshToken(refreshToken)
    }


    override suspend fun getRefreshToken(): String
    = getOrThrowUserNotAuth { accessTokenStore.getRefreshToken() }

    override suspend fun removeTokens() {
        withContext(io){
            userDao.deleteByCustomerId(getCustomerId())
            accessTokenStore.removeTokens()
            userDataStore.removeUserProfileId()
        }
    }

}


@Throws(UserNotFoundException::class)
suspend fun <T> getOrThrowUserNotAuth(block: suspend () -> T?): T{
    return block() ?: throw UserNotFoundException()
}

//  private val userDataStore: UserDataStore,