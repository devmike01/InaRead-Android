package dev.gbenga.inaread.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.NullableText


data class HomeScaffoldConfig(
    val title: String? = null,
    val navigationClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScaffold(inaScaffoldConfig: HomeScaffoldConfig, content: @Composable (PaddingValues) -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        inaScaffoldConfig.title.NullableText(
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                actions = {

                }
            )
        }
    ) {
        content(it)
    }
}

@Preview
@Composable
fun PreviewInaScaffold(){
    HomeScaffold(HomeScaffoldConfig("Mon Jan, 2021", navigationClick = {

    })){
        Box(modifier = Modifier.padding(it).background(MaterialTheme.colorScheme.primary))
    }
}