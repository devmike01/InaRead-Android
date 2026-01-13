package dev.gbenga.inaread.ui.customs

import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun InaCard(modifier: Modifier = Modifier,
            colors: CardColors= CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.typography.bodyMedium.color
            ),
            cardElevation: CardElevation = CardDefaults.cardElevation(),
            content: @Composable () -> Unit){
    Card(
        modifier = modifier,
        elevation = cardElevation,
        colors = colors,) {
        content()
    }
}

