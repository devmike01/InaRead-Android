package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.auth.SignUpResponse
import dev.gbenga.inaread.data.db.UserDao
import dev.gbenga.inaread.data.db.entities.UserEntity
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.network.AuthenticationService
import dev.gbenga.inaread.domain.repository.AuthRepository
import dev.gbenga.inaread.domain.services.AuthApiService
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class AuthRepositoryImpl(private val authApiService: AuthenticationService,
                         private val userDao: UserDao,
                         private val io: CoroutineContext) : AuthRepository {

    override suspend fun authenticate(request: LoginRequest): ApiResult<LoginResponse> {
        return authApiService.authenticate(request).doIfSuccess {

            userDao.save(UserEntity(
                it.customerId,
                it.username,
                it.email,
                it.meterNo,
                it.countryId,
                it.meterCategoryId,
                it.createdAt,
                it.enabled,
                false
            ))
        }
    }

    override suspend fun getProfile(userId: String): ApiResult<LoginResponse> {
        return withContext(io){
            val profile = userDao.getProfile(userId).map {
                LoginResponse(
                    it.customerId,
                    it.meterNo,
                    it.countryId,
                    it.meterCategoryId,
                    it.createdAt,
                    it.enabled,
                    it.username,
                    it.email,
                    null,
                )
            }
            ApiResult(data = profile.firstOrNull())
        }
    }

    override suspend fun signUp(request: SignUpRequest): ApiResult<SignUpResponse> {
        return authApiService.signUp(request)
    }
}

suspend fun <T> ApiResult<T>.doIfSuccess(block: suspend (T) -> Unit): ApiResult<T>{
    if (this.isSuccess && this.data != null){
        block(this.data)
    }
    return this
}