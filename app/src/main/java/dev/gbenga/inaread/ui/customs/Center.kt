package dev.gbenga.inaread.ui.customs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun VerticalCenter(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.Center, modifier =modifier) {
        content()
    }
}

@Composable
fun HorizontalCenter(modifier: Modifier= Modifier, content: @Composable () -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier =modifier) {
        content()
    }
}