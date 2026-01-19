package dev.gbenga.inaread.domain.repository

import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.model.ApiResult

interface AuthRepository {

    suspend fun authenticate(request: LoginRequest): ApiResult<LoginResponse>

    suspend fun getProfile(): ApiResult<LoginResponse>

    suspend fun signUp(request: SignUpRequest) : ApiResult<String>

}