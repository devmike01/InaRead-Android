package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.auth.SignUpResponse
import dev.gbenga.inaread.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {

    override fun login(request: LoginRequest): LoginResponse {
        TODO("Not yet implemented")
    }

    override fun getProfile(): LoginResponse {
        TODO("Not yet implemented")
    }

    override fun signUp(request: SignUpRequest): SignUpResponse {
        TODO("Not yet implemented")
    }
}