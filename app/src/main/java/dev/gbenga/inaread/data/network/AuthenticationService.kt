package dev.gbenga.inaread.data.network

import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.auth.SignUpResponse
import dev.gbenga.inaread.data.model.ApiResult
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST(EndPoints.Login)
    suspend fun authenticate(@Body loginRequest: LoginRequest): ApiResult<LoginResponse>

    @POST(EndPoints.SignUp)
    suspend fun signUp(@Body request: SignUpRequest): ApiResult<SignUpResponse>

}