package dev.gbenga.inaread.domain.repository

import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.auth.SignUpResponse

interface AuthRepository {

    fun login(request: LoginRequest): LoginResponse

    fun getProfile(): LoginResponse

    fun signUp(request: SignUpRequest) : SignUpResponse

}