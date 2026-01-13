package dev.gbenga.inaread.ui.customs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.home.VectorInaTextIcon

@Composable
fun FailedUiStateComponent(message: String) {
    XYAxisCenter(modifier = Modifier
        .padding(DimenTokens.Padding.normal).fillMaxWidth()) {
        VectorInaTextIcon(
            icon = Icons.Outlined.ErrorOutline,
            label = "Error",
            value = message,
            color = 0xFFC62828
        )
    }
}