package dev.gbenga.inaread.ui.metric

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.inaread.R
import dev.gbenga.inaread.data.model.Appliance
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.BarChart
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.ui.theme.Indigo400

@Composable
fun MetricScreen(viewModel: MetricViewModel = hiltViewModel()) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    UnitLaunchEffect {
        viewModel.watchEvents()
        viewModel.populate()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()
        .padding(horizontal = DimenTokens.Padding.normal),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(DimenTokens.Padding.normal)) {

        item {
            AllTimeTitle("Your Usage for the year")
            BarChart(uiState.monthChartValues,
                modifier = Modifier.animateItem())

        }

        item {
            AllTimeTitle("Your Appliances")
            uiState.appliances.forEach {
                ApplianceItem(
                    modifier = Modifier.animateItem(),
                    appliance = it)
            }
        }
    }
}

@Composable
fun AllTimeTitle(title: String){
    Text(title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = DimenTokens.Padding.normal))
}


@Composable
fun ApplianceItem(modifier: Modifier = Modifier, appliance: Appliance){
    Card(modifier = modifier
        .padding(vertical = DimenTokens.Padding.small)
        .height(100.dp)
        .fillMaxWidth(),
    ) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Indigo400)
            .padding(DimenTokens.Padding.normal),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Column {
                Text(appliance.name, style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = DimenTokens.Padding.xSmall))
                Text("Added on: ${appliance.createdOn}",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W800))
            }
            Box(modifier = Modifier
                .size(70.dp)
            ){
                Icon(painter = painterResource(R.drawable.outline_electric_meter_24),
                    contentDescription = "Electric rating",
                    modifier = Modifier.align(Alignment.TopCenter).size(DimenTokens.Icon.Large))
                Text(appliance.rating,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                    modifier = Modifier.align(Alignment.BottomCenter))
            }
        }
    }
}

@Composable
fun YearlyUsageCard(modifier: Modifier = Modifier,
                      title: String,
                      subTitle: String, onClick: () -> Unit){
    Card(
        modifier = modifier,
        onClick = onClick) {
        val annotatedString = buildAnnotatedString {
            withStyle(SpanStyle(
                fontWeight = FontWeight.W700,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )){
                append("$title\n")
            }
            append(subTitle)
        }
        Text(annotatedString,
            style = MaterialTheme.typography.bodySmall)
    }
}


@Preview
@Composable
fun PreviewApplianceItem(){
    ApplianceItem(
        Modifier,
        Appliance("Fan", "200kWh", createdOn = "12 January, 2026")
    )
}