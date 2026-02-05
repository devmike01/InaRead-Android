package dev.gbenga.inaread.ui.mapper

import android.util.Log
import dev.gbenga.inaread.data.auth.UiLogin
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.LoginOutput
import dev.gbenga.inaread.data.repository.AuthRepositoryImpl.Companion.TAG
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle


fun <T> RepoResult<T>.toUiState(): UiState<T> =  when(this){
    is RepoResult.Success -> {
        UiState.Success(data)
    }
    is RepoResult.Error -> {
        UiState.Error(message)
    }
}




fun RepoResult<LoginOutput>.toUiStateLogin(): UiStateWithIdle<UiLogin>{
    Log.d(TAG, "repo->: $this")
    return when(this){
        is RepoResult.Success -> {
            UiStateWithIdle.Success(data.toUILogin())
        }
        is RepoResult.Error -> {
            UiStateWithIdle.Error(message)
        }
    }
}

fun LoginOutput.toUILogin(): UiLogin{
    return UiLogin(
        customerId,
        meterNo,
        countryId,
        meterCategoryId,
        createdAt,
        enabled,
        username,
        email
    )
}