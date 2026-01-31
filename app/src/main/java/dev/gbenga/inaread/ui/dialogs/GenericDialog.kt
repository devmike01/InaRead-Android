package dev.gbenga.inaread.ui.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
fun GenericDialog(show: Boolean,
                  onDismissRequest: () -> Unit,
                  content: @Composable () -> Unit) {
    if (show){
        Dialog(onDismissRequest = onDismissRequest,) {
            content()
        }
    }
}

@Composable
fun LoadingDialog(show: Boolean,){
    GenericDialog(show, onDismissRequest = {}) {
        Box(modifier = Modifier.wrapContentSize()) {
            CircularProgressIndicator()
        }
    }
}