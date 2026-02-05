package dev.gbenga.inaread.data.repository

import android.util.Log
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.db.UserDao
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.mapper.map
import dev.gbenga.inaread.data.mapper.toLoginOutput
import dev.gbenga.inaread.data.mapper.toLoginRequest
import dev.gbenga.inaread.data.mapper.toSignUpOutput
import dev.gbenga.inaread.data.mapper.toUserEntity
import dev.gbenga.inaread.data.model.LoginInput
import dev.gbenga.inaread.data.model.LoginOutput
import dev.gbenga.inaread.data.model.SignUpOutput
import dev.gbenga.inaread.data.network.AuthenticationService
import dev.gbenga.inaread.domain.InaEncryptedPrefs
import dev.gbenga.inaread.domain.repository.AuthRepository
import dev.gbenga.inaread.tokens.StringTokens
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(private val authApiService: AuthenticationService,
                         private val userDao: UserDao,
                         private val inaEncryptedPrefs: InaEncryptedPrefs,
                         private val io: CoroutineDispatcher
) : AuthRepository, NetworkRepository() {

    companion object{
        const val TAG = "AuthRepository"
        const val ACCESS_TOKEN = "AuthRepositoryImpl.ACCESS_TOKEN"
    }

    // Change
    override suspend fun authenticate(input: LoginInput): RepoResult<LoginOutput> =safeCall {
            authApiService.authenticate(input.toLoginRequest())
        }.map {
            Log.d(TAG, "Persisting AND Mapping values")
        inaEncryptedPrefs.addString(ACCESS_TOKEN, it.authToken?.access)
        userDao.save(it.toUserEntity())
            it.toLoginOutput()
        }



    override suspend fun getProfile(userId: String): RepoResult<LoginOutput> {
        return withContext(io){
            userDao.getProfile(userId).firstOrNull()?.let {

                RepoResult.Success(data = it.toLoginOutput())
            } ?: RepoResult.Error(StringTokens.Auth.NoProfileWithUserId)
        }
    }

    override suspend fun signUp(request: SignUpRequest): RepoResult<SignUpOutput> = safeCall {
        authApiService.signUp(request)
    }.map { it.toSignUpOutput() }
}
