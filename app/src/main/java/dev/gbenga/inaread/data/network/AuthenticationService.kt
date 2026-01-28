package dev.gbenga.inaread.data.network

import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.auth.SignUpResponse
import dev.gbenga.inaread.data.model.ApiResult
import retrofit2.http.POST

interface AuthenticationService {

    @POST(EndPoints.Login)
    suspend fun authenticate(loginRequest: LoginRequest): ApiResult<LoginResponse>

    @POST(EndPoints.SignUp)
    suspend fun signUp(request: SignUpRequest): ApiResult<SignUpResponse>

}