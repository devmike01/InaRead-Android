package dev.gbenga.inaread.data.model

import dev.gbenga.inaread.tokens.StringTokens

data class ApiResult<T>(val data: T? = null,
                        val error: String? = null){

    val isSuccess = data != null
    val isError = error != null

//    fun successful(onSuccess: (T) -> Unit): ApiResult<T>{
//        if (data != null && error == null){
//            onSuccess(data)
//        }else if ()
//        return this
//    }
//
//    fun failed(onFailed: (String) -> Unit): ApiResult<T>{
//        if (error != null && data == null){
//            onFailed(error)
//        }else if(error == null){
//            onFailed(StringTokens.UknownErrorOccured)
//        }
//        return this
//    }


}