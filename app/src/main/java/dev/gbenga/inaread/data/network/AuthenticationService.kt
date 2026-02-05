package dev.gbenga.inaread.data.network

import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.auth.SignUpResponse
import dev.gbenga.inaread.data.model.ApiResult
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST(EndPoints.Login)
    suspend fun authenticate(@Body loginRequest: LoginRequest): Response<ApiResult<LoginResponse>>

    @POST(EndPoints.SignUp)
    suspend fun signUp(@Body request: SignUpRequest): Response<ApiResult<SignUpResponse>>

}