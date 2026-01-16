package dev.gbenga.inaread.ui.customs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.metric.AllTimeTitle

@Composable
fun TitledColumn(title: String,
                 modifier: Modifier = Modifier,
                 endText: String? = null,
                 horizontalAlignment: Alignment.Horizontal = Alignment.Start,
                 content: @Composable () -> Unit){
    Column(modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = Arrangement.spacedBy(DimenTokens.Padding.small)) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            AllTimeTitle(title)
            endText?.let {
                TextButton(onClick = {}) {
                    Text(it,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.W800))
                }
            }

        }
        content()
    }
}