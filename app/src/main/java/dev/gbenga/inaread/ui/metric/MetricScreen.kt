package dev.gbenga.inaread.ui.metric

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.inaread.R
import dev.gbenga.inaread.data.model.Appliance
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.BarChart
import dev.gbenga.inaread.ui.customs.UiStateWithLoadingBox
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.ui.theme.Indigo400
import dev.gbenga.inaread.ui.theme.Indigo900

@Composable
fun MetricScreen(viewModel: MetricViewModel) {

    Column {

        YearPickerBar(viewModel)

        MetricScreenContent(viewModel = viewModel)

    }
}

@Composable
fun YearPickerBar(viewModel: MetricViewModel){

    val yearState = rememberYearPickerState()

    LaunchedEffect(yearState.state) {
        viewModel.getYearlyUsage(yearState.state)
    }

    YearPicker(yearState,
        modifier = Modifier.padding(
            bottom = DimenTokens.Padding.Normal)){

    }
}

@Composable
fun MetricScreenContent(viewModel: MetricViewModel){
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    LazyColumn(modifier = Modifier.fillMaxSize()
        .padding(horizontal = DimenTokens.Padding.Normal),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(DimenTokens.Padding.Normal)) {

        item {
            AllTimeTitle("My Usage for the year")
            AnimatedContent(uiState.monthChartValues) { monthChartValues ->
                UiStateWithLoadingBox(monthChartValues,
                    errorRequest = {
                        Box(modifier = Modifier.fillMaxSize()){
                            Text(it, modifier = Modifier.align(Alignment.Center))
                        }
                    }) {
                    BarChart(it,
                        modifier = Modifier.animateItem())
                }
            }


        }

        item {
            AllTimeTitle("My Appliances")
            UiStateWithLoadingBox(uiState.appliances, errorRequest = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(it)
                }
            }) { appliances ->
                appliances.forEach {
                    ApplianceItem(
                        modifier = Modifier.animateItem(),
                        appliance = it)
                }
            }
        }
    }
}

@Composable
fun AllTimeTitle(title: String){
    Text(title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W700),
        modifier = Modifier.padding(bottom = 15.dp))
}


@Composable
fun ApplianceItem(modifier: Modifier = Modifier, appliance: Appliance){
    Card(modifier = modifier
        .padding(vertical = DimenTokens.Padding.XSmall)
        .wrapContentHeight()
        .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Indigo900
        ),
        border = BorderStroke(DimenTokens.Padding.Small, Indigo900)
    ) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(DimenTokens.Padding.Normal),
            horizontalArrangement = Arrangement.spacedBy(DimenTokens.Padding.Normal),
            verticalAlignment = Alignment.CenterVertically) {


            Column(modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(DimenTokens.Radius.large))
                .background(Color(0XFFD84315))
                .padding(DimenTokens.Padding.XSmall),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("${appliance.rating}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.W700,
                    ),)
                Text(appliance.ratingUnit,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.W700,
                        fontSize = 11.sp
                        // color = MaterialTheme.colorScheme.primary
                    ),)
            }

            VerticalDivider(
                modifier = Modifier.height(DimenTokens.Padding.Large),
                thickness = 1.dp)

            Column {
                Text(appliance.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.W900,
                      //  color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(bottom = DimenTokens.Padding.XSmall))
                Text("Added on: ${appliance.createdOn}",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W800))
            }
        }
    }
}


@Preview
@Composable
fun PreviewApplianceItem(){
    ApplianceItem(
        Modifier,
        Appliance(name="Fan", rating = 200.toBigDecimal(),
            ratingUnit="KwH",
            applianceTypeId =12,
            createdOn = "12 January, 2026")
    )
}