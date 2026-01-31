package dev.gbenga.inaread.domain.repository

import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.auth.SignUpResponse
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.LoginInput
import dev.gbenga.inaread.data.model.LoginOutput
import dev.gbenga.inaread.data.model.SignUpOutput

interface AuthRepository {

    suspend fun authenticate(input: LoginInput): RepoResult<LoginOutput>

    suspend fun getProfile(userId: String): RepoResult<LoginOutput>

    suspend fun signUp(request: SignUpRequest) : RepoResult<SignUpOutput>

}