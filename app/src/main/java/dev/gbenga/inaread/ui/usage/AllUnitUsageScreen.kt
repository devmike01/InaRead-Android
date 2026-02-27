package dev.gbenga.inaread.ui.usage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.gbenga.inaread.data.model.MonthlyUsage
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.InaCard
import dev.gbenga.inaread.ui.customs.InaScaffold
import dev.gbenga.inaread.ui.customs.InaScaffoldConfig
import dev.gbenga.inaread.ui.customs.UiStateWithLoadingBox
import dev.gbenga.inaread.ui.customs.color
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.utils.nav.InaScreen
import dev.gbenga.inaread.utils.rememberNavigationDelegate

@Composable
fun AllUnitUsageScreen(viewModel: AllUnitUsageViewModel = hiltViewModel(),
                       navigator: NavController) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val navigatorDelegate = rememberNavigationDelegate(navigator)

    UnitLaunchEffect {
        viewModel.navigator.collect {
            navigatorDelegate.handleEvents(it)
        }
    }


    InaScaffold(
        modifier = Modifier
            .fillMaxSize(),
        inaScaffoldConfig = InaScaffoldConfig(
            title = "Lifetime Electricity Usages",
            navigationClick = {
                viewModel.goBack()
            }
        )) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)){
            UiStateWithLoadingBox(uiState.monthlyUsageItems,
                errorRequest = {
                    Text(it, style = MaterialTheme.typography.bodySmall
                        .copy(color = MaterialTheme.colorScheme.tertiary))
                },) {  monthlyUsages ->
                LazyColumn(modifier = Modifier, verticalArrangement = Arrangement
                    .spacedBy(DimenTokens.Padding.XSmall) ) {
                    items(monthlyUsages.size){
                        UsageCardItem(
                            item = monthlyUsages[it],
                            onClick = {
                                navigator.navigate(InaScreen.AllUnitUsageDetailsScreen)
                            })
                    }
                }
            }
        }
    }

}

@Composable
fun UsageCardItem(modifier: Modifier = Modifier,
                  onClick: () -> Unit,
                  item: MonthlyUsage){
    InaCard(modifier = modifier
        .height(DimenTokens.UnitUsage.CardHeight)
        .fillMaxWidth()
        .padding(horizontal = DimenTokens.Padding.Small)
        .padding(top = DimenTokens.Padding.Small)) {
        TextButton(onClick = onClick) {
            Row(modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically) {
                DateItem(day = item.day,
                    month = item.month)

                VerticalDivider(modifier = Modifier
                    .padding(DimenTokens.Padding.Small)
                    .fillMaxHeight(), thickness = .5.dp,
                    color = 0xFF9FA8DA.color())
                DetailItem(
                    usedPower = item.kilowatt,
                    period = item.period,
                    cost = item.cost
                )
            }
        }
    }
}

@Composable
fun DetailItem(modifier: Modifier = Modifier,
               usedPower: String, period: String, cost: String){
    val detailString = buildAnnotatedString {
        withStyle(style = SpanStyle(
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
        )){
            append(usedPower)
        }

        append("(KWh)")

        withStyle(style = SpanStyle(
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary
        )){
            append(" IN ")
        }

        withStyle(style = SpanStyle(
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )){
            append("$period\n")
        }

        withStyle(style = SpanStyle(
            color = MaterialTheme.colorScheme.primary
        )){
            append("Costs ")
        }
        withStyle(style = SpanStyle(
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )){
            append(cost)
        }
    }
    Text(modifier = modifier,
        text =detailString,
        style = MaterialTheme.typography.bodySmall
        .copy(
            color = 0XffE8EAF6.color(),
            lineHeight = 2.5.em
        ) )
}

@Composable
fun DateItem(modifier: Modifier =Modifier,
             day: String,
             month: String,){
    Column(
        modifier = modifier.padding(DimenTokens.Padding.Small)
    ) {
        val dateString = buildAnnotatedString {
            withStyle(style = SpanStyle(
                fontSize = DimenTokens.FontSize.HeadlineMedium,
                color = MaterialTheme.colorScheme.primary
            )){
                append("$day\n\n")
            }

            withStyle(style = SpanStyle(
                fontSize = DimenTokens.FontSize.BodyMedium
            )){
                append(month)
            }
        }
        Text(dateString, style = MaterialTheme
            .typography.bodySmall.copy(
                color = 0XffE8EAF6.color(),
                fontSize = DimenTokens.FontSize.BodySmall,
                lineHeight = 1.1.em
                ),
            textAlign = TextAlign.Center)
    }
}