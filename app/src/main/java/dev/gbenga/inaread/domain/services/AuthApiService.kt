package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.model.ApiResult

interface AuthApiService {

    suspend fun authenticate(request: LoginRequest): ApiResult<LoginResponse>

    suspend fun signUp(request: SignUpRequest): ApiResult<String>

    suspend fun forgotPassword(email: String): ApiResult<String>

    suspend fun validateOTP(otp: String): ApiResult<String>

    suspend fun sendOTP(email: String): ApiResult<String>
}