package dev.gbenga.inaread.ui.customs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle

@Composable
fun <T> UiStateWithLoadingBox(uiState: UiState<T>,
                   errorRequest: @Composable (String) -> Unit,
                   content: @Composable (T) -> Unit,) {
    when(uiState){
        is UiState.Loading ->{
            HorizontalCenter(modifier = Modifier.wrapContentHeight()
                .fillMaxWidth()) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            content(uiState.data)
        }
        is UiState.Error -> {
            errorRequest(uiState.message)
        }

    }
}



@Composable
fun <T> UiStateWithLoadingBox(uiState: UiStateWithIdle<T>,
                              errorRequest: @Composable (String) -> Unit,
                              content: @Composable (T) -> Unit,) {
    when(uiState){
        is UiStateWithIdle.Loading ->{
            HorizontalCenter(modifier = Modifier.wrapContentHeight()
                .fillMaxWidth()) {
                CircularProgressIndicator()
            }
        }
        is UiStateWithIdle.Success -> {
            content(uiState.data)
        }
        is UiStateWithIdle.Error -> {
            errorRequest(uiState.message)
        }

        else -> {}
    }
}