package dev.gbenga.inaread.ui.customs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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


data class InaScaffoldConfig(
    val title: String? = null,
    val navigationClick: () -> Unit ={},
    val actions: @Composable () -> Unit ={}
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InaScaffold(
    modifier: Modifier = Modifier,
    inaScaffoldConfig: InaScaffoldConfig = InaScaffoldConfig(),
    content: @Composable (PaddingValues) -> Unit) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    inaScaffoldConfig.title.NullableText()
                },
                navigationIcon = {
                    IconButton(onClick = inaScaffoldConfig.navigationClick,
                        modifier = Modifier
                            .padding(DimenTokens.Padding.Small)
                            .border(width = 1.dp, MaterialTheme
                                .colorScheme.secondary.copy(alpha = .5f),
                                RoundedCornerShape(DimenTokens.Radius.small))) {
                        Icon(imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "Go Back")
                    }

                },
                actions = {
                    inaScaffoldConfig.actions()
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
    InaScaffold(inaScaffoldConfig =InaScaffoldConfig("Back", navigationClick = {

    })){
        Box(modifier = Modifier.padding(it).background(MaterialTheme.colorScheme.primary))
    }
}