package dev.gbenga.inaread.ui.customs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.ui.home.VectorInaTextIcon

@Composable
fun FailedUiStateComponent(message: String, onTryAgainClick: () -> Unit = {}) {
    XYAxisCenter(modifier = Modifier
        .padding(DimenTokens.Padding.Normal)
        .fillMaxWidth()) {
        Text(message)
        Spacer(Modifier.padding(DimenTokens.Padding.Small))
        OutlinedButton (onClick = onTryAgainClick) {
            Text(StringTokens.Button.TryAgain,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center
                )
        }
    }
}