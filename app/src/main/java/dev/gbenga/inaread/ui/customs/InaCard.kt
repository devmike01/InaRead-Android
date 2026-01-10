package dev.gbenga.inaread.ui.customs

import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun InaCard(modifier: Modifier = Modifier,
            colors: CardColors= CardDefaults.cardColors(),
            content: @Composable () -> Unit){
    Card(modifier = modifier,
        colors = colors) {
        content()
    }
}

