package dev.gbenga.inaread.ui.customs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.gbenga.inaread.tokens.DimenTokens

@Composable
fun TitledColumn(title: String,
                 modifier: Modifier = Modifier,
                 horizontalAlignment: Alignment.Horizontal = Alignment.Start,
                 content: @Composable () -> Unit){
    Column(modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = Arrangement.spacedBy(DimenTokens.Padding.small)) {
        Text(title, style = MaterialTheme.typography.titleMedium
            .copy(color = Color(0xB9FFFFFF)))
        content()
    }
}