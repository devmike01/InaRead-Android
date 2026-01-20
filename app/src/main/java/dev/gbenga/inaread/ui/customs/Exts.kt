package dev.gbenga.inaread.ui.customs

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle
import java.util.concurrent.CancellationException


@Composable
fun String?.NullableText(modifier: Modifier = Modifier, style: TextStyle= LocalTextStyle.current){
    this?.let {
        Text(it, style = style, modifier = modifier)
    }
}

@Composable
fun IconConfig?.NullableIcon(){
    this?.let {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = description
        )
    }
}

fun <T> Result<T>.toUiState(): UiState<T>{
    return fold( onSuccess = {
        UiState.Success(it)
    },
        onFailure = {
            UiState.Error(it.message ?: StringTokens.UknownErrorOccured)
        })
}


suspend  fun <T> uiStateRunCatching(block: suspend () -> T): UiState<T>{
    return try {
        UiState.Success(block())
    }catch (exception: Exception){
        UiState.Error(exception.message ?: StringTokens.UknownErrorOccured)
    }
}

suspend  fun <T> uiStateWithIdleRunCatching(block: suspend () -> T): UiStateWithIdle<T>{
    return try {
        UiStateWithIdle.Success(block())
    }catch (exception: Exception){
        if (exception is CancellationException){
            throw exception
        }
        UiStateWithIdle.Error(exception.message ?: StringTokens.UknownErrorOccured)
    }
}


data class IconConfig(@param:DrawableRes val iconResId: Int, val description: String?)

fun Long.color(): Color = Color(this)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "store",
)
