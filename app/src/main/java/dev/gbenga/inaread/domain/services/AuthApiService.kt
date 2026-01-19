package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.model.ApiResult

interface AuthApiService {

    fun authenticate(email: String, password: String): ApiResult<LoginResponse>

    fun signUp(username: String, password: String, email: String): ApiResult<String>

    fun forgotPassword(email: String): ApiResult<String>

    fun validateOTP(otp: String): ApiResult<String>

    fun sendOTP(email: String): ApiResult<String>
}