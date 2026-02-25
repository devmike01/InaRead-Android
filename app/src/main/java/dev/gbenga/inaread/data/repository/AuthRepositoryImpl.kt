package dev.gbenga.inaread.data.repository

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
import dev.gbenga.inaread.domain.services.AuthenticationApiService
import dev.gbenga.inaread.domain.repository.AuthRepository
import dev.gbenga.inaread.utils.UserNotFoundException
import dev.gbenga.inaread.utils.UserProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(private val authApiService: AuthenticationApiService,
                         private val userDao: UserDao,
                         private val userProvider: UserProvider,
                         private val io: CoroutineDispatcher
) : AuthRepository, NetworkRepository() {

    companion object{
        const val TAG = "AuthRepository"
    }

    // Change
    override suspend fun authenticate(input: LoginInput): RepoResult<LoginOutput> =safeCall {
            authApiService.authenticate(input.toLoginRequest())
        }.map {
            withContext(io){
                it.authToken?.let { access ->
                    userProvider.setAccessToken(access.access)
                    userProvider.setRefreshToken(access.refresh)
                }

                userProvider.setCustomerId(it.customerId)
                userDao.save(it.toUserEntity())
                it.toLoginOutput()
            }
        }


    override suspend fun getProfile(userId: String): RepoResult<LoginOutput> {
        return withContext(io){
            userDao.getProfile(userId).let {
                RepoResult.Success(data = it.toLoginOutput())
            }
        }
    }

    override suspend fun signUp(request: SignUpRequest): RepoResult<SignUpOutput> = safeCall {
        authApiService.signUp(request)
    }.map { it.toSignUpOutput() }

    override suspend fun signOut(): RepoResult<String> = safeCall {
        authApiService.signOut(userProvider.getCustomerId())
            .apply {
                if (isSuccessful){
                    userProvider.removeTokens()
                }
            }
    }

    override suspend fun isSignedIn(): Boolean {
        return try {
            userProvider.getAccessToken()
            true
        }catch (unf: UserNotFoundException){
            false
        }
    }
}
