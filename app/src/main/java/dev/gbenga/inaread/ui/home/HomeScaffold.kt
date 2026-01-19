package dev.gbenga.inaread.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.NullableText
import dev.gbenga.inaread.ui.customs.VerticalCenter
import dev.gbenga.inaread.ui.theme.Indigo800


data class HomeScaffoldConfig(
    val title: String? = null,
    val subTitle: String? = null,
    val navigationClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScaffold(inaScaffoldConfig: HomeScaffoldConfig,
                 profileInitial: String,
                 content: @Composable () -> Unit) {

    Column(verticalArrangement = Arrangement.SpaceBetween) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            modifier = Modifier.height(DimenTokens.Size.topbar),
            title = {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center) {
                    inaScaffoldConfig.title.NullableText(
                        style = MaterialTheme.typography.bodyMedium
                    )

                    inaScaffoldConfig.subTitle.NullableText(
                        style = MaterialTheme.typography
                            .headlineMedium.copy(fontWeight = FontWeight.W800)
                    )

                }
            },
            actions = {
                InitialComponent(profileInitial)
            }
        )
        content()
    }
}


@Composable
fun InitialComponent(initial: String, modifier: Modifier =Modifier){
    VerticalCenter(modifier = modifier.fillMaxHeight()) {
        Box(modifier = Modifier.size(60.dp)
            .padding(DimenTokens.Padding.Small)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.tertiary)
        ) {
            initial.NullableText(modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W800,
                    color = Indigo800))
        }
    }
}

@Preview
@Composable
fun PreviewInaScaffold(){
    HomeScaffold(HomeScaffoldConfig("Mon Jan, 2021",
        navigationClick = {

    }), profileInitial = "G"){
        Box(modifier = Modifier.padding().background(MaterialTheme.colorScheme.primary))
    }
}