package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.domain.repository.AuthRepository
import dev.gbenga.inaread.domain.services.AuthApiService
import kotlin.coroutines.CoroutineContext

class AuthRepositoryImpl(private val authApiService: AuthApiService,
    private val io: CoroutineContext) : AuthRepository {

    override suspend fun authenticate(request: LoginRequest): ApiResult<LoginResponse> {
        return authApiService.authenticate(request)
    }

    override suspend fun getProfile(): ApiResult<LoginResponse> {
        TODO("Not yet implemented") // Get profile from local db
    }

    override suspend fun signUp(request: SignUpRequest): ApiResult<String> {
        return authApiService.signUp(request)
    }
}