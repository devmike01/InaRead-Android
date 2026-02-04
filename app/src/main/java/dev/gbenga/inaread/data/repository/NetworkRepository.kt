package dev.gbenga.inaread.data.repository

import com.google.gson.Gson
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.tokens.StringTokens
import retrofit2.Response

abstract class NetworkRepository(val gson: Gson = Gson()) {

    suspend fun <T> safeCall(onApiCall: suspend () -> Response<ApiResult<T>>): RepoResult<T>{
        return try {
            onApiCall().let { call ->
                val body =  call.body()
                if (call.isSuccessful && body?.data != null){
                    RepoResult.Success(body.data)
                }else{
                    val error =
                        body?.error ?:
                        gson.fromJson(call.errorBody()?.string(), ApiResult::class.java).error
                    RepoResult.Error(error ?: StringTokens.UnknownErrorOccured)
                }
            }
        }catch (exception: Exception){
            exception.printStackTrace()
            RepoResult.Error(StringTokens.UnknownErrorOccured)
        }
    }




}