package dev.gbenga.inaread.ui.customs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.gbenga.inaread.utils.UiState

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

