package dev.gbenga.inaread.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.repository.AuthRepositoryImpl.Companion.TAG
import dev.gbenga.inaread.tokens.StringTokens
import kotlinx.coroutines.runBlocking
import retrofit2.Response

abstract class NetworkRepository(val gson: Gson = Gson()) {

    suspend fun <T> safeCallV2(onApiCall: suspend () -> ApiResult<T>): RepoResult<T>{
        return try{
            val call = onApiCall()
            if (call.isSuccess){
                RepoResult.Success(call.data!!)
            }else{
                RepoResult.Error(call.error ?: StringTokens.UnknownErrorOccured)
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            RepoResult.Error(StringTokens.UnknownErrorOccured)
        }
    }


        suspend fun <T> safeCall(onApiCall: suspend () -> Response<ApiResult<T>>): RepoResult<T>{
        return try {
            val call = onApiCall()
            val body =  call.body()
            if (call.isSuccessful && body?.data != null){
                RepoResult.Success(body.data)
            }else{
                val error =
                    body?.error ?:
                    gson.fromJson(call.errorBody()?.string(),
                        ApiResult::class.java).error

                RepoResult.Error(error ?: StringTokens.UnknownErrorOccured)
            }
        }catch (jE: JsonSyntaxException){
            RepoResult.Error(StringTokens.ServerErrorOccured)
        }catch (exception: Exception){
            exception.printStackTrace()
            RepoResult.Error(StringTokens.UnknownErrorOccured)
        }
    }




}