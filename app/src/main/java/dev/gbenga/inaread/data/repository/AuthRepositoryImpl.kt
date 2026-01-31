package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.auth.SignUpResponse
import dev.gbenga.inaread.data.db.UserDao
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.LoginInput
import dev.gbenga.inaread.data.model.LoginOutput
import dev.gbenga.inaread.data.network.AuthenticationService
import dev.gbenga.inaread.domain.repository.AuthRepository
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.data.mapper.toLoginOutput
import dev.gbenga.inaread.data.mapper.toLoginRequest
import dev.gbenga.inaread.data.mapper.toSignUpOutput
import dev.gbenga.inaread.data.mapper.toUserEntity
import dev.gbenga.inaread.data.model.SignUpOutput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(private val authApiService: AuthenticationService,
                         private val userDao: UserDao,
                         private val io: CoroutineDispatcher
) : AuthRepository {

    // Change
    override suspend fun authenticate(input: LoginInput): RepoResult<LoginOutput> {
        val auth = authApiService.authenticate(input.toLoginRequest())

        return if (auth.isSuccess){
            val data = auth.data ?: return RepoResult.Error(StringTokens.UnknownErrorOccured)
            withContext(io){
                userDao.save(data.toUserEntity()) }
            RepoResult.Success(data.toLoginOutput())
        }else{
            RepoResult.Error(auth.error ?:StringTokens.UnknownErrorOccured)
        }
    }

    override suspend fun getProfile(userId: String): RepoResult<LoginOutput> {
        return withContext(io){
            userDao.getProfile(userId).firstOrNull()?.let {
                RepoResult.Success(data = it.toLoginOutput())
            } ?: RepoResult.Error(StringTokens.Auth.NoProfileWithUserId)
        }
    }

    override suspend fun signUp(request: SignUpRequest): RepoResult<SignUpOutput> {
        val apiResult =  authApiService.signUp(request)
        return if (apiResult.isSuccess ){
            val data = apiResult.data ?: return RepoResult.Error(StringTokens.UnknownErrorOccured)
            RepoResult.Success(data.toSignUpOutput())
        }else {
            RepoResult.Error( apiResult.error ?: StringTokens.UnknownErrorOccured)

        }
    }
}

suspend fun <T> ApiResult<T>.doIfSuccess(block: suspend (T) -> T): RepoResult<T> {
    if (this.isSuccess && this.data != null){
        return RepoResult.Success(block(this.data))
    }
    return RepoResult.Error("")
}

//fun <T, R> RepoResult<T>.map(onMap: (T) -> R): RepoResult<R>{
//    return try {
//        when(this){
//            is RepoResult.Success<*> -> {
//
//            }
//            is RepoResult.Error -> {
//                RepoResult
//            }
//        }
////        if (this.isSuccess && data != null){
////            ApiResult(data = onMap(this.data))
////        }else {
////            ApiResult(error = this.error)
////        }
//    }catch (nse: NoSuchElementException){
//        RepoResult.Error(nse.message ?: StringTokens.UknownErrorOccured)
//    }
//}
