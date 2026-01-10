package dev.gbenga.inaread.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CandlestickChart
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PieChartOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.InaCard
import dev.gbenga.inaread.ui.customs.color

@Composable
fun LifeTimeStats(lifeTimeReading: HomeStat,
                  monthlyStat: HomeStat,
                  costStat: HomeStat,
                  chartData: List<Float>, modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (lifetime, monthly, cost) = createRefs()
        LifeTimeReadingCard(lifeTimeReading,
            chartData,
            modifier = Modifier.constrainAs(lifetime){
            start.linkTo(parent.start,)
        }.fillMaxWidth(.5f)
                .fillMaxHeight(.5f))

        NoChartCard(monthlyStat, Modifier.constrainAs(monthly){
            top.linkTo(parent.top)
            end.linkTo(parent.end,)
            start.linkTo(lifetime.end, margin = DimenTokens.Padding.normal)
        })

        NoChartCard(costStat, Modifier
            .padding(vertical = DimenTokens.Padding.small).constrainAs(cost){
            end.linkTo(parent.end,)
            start.linkTo(lifetime.end, margin = DimenTokens.Padding.normal)
            top.linkTo(monthly.bottom)
        })
    }
}


@Composable
private fun NoChartCard(itemData: HomeStat,
                        modifier: Modifier = Modifier){
    InaCard(modifier = modifier) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth(.44f)
            .height(100.dp)
            .padding(start = DimenTokens.Padding.normal)) {
            val (stats, chartIcon) = createRefs()
            Column(modifier = Modifier.constrainAs(stats){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }) {
                Text(itemData.reading,
                    style = MaterialTheme.typography.headlineMedium
                        .copy(fontWeight = FontWeight.W600))
                Text(itemData.label,
                    style = MaterialTheme.typography.bodySmall
                        .copy(fontWeight = FontWeight.W600),)
            }
            InaIcon(itemData, modifier = Modifier.constrainAs(chartIcon){
                end.linkTo(parent.end, margin = DimenTokens.Padding.xSmall)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }.size(90.dp))

        }
    }
}

@Composable
fun LifeTimeReadingCard(lifeTimeReading: HomeStat,
                        data: List<Float>,
                        modifier: Modifier = Modifier){
    InaCard(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween) {
            Column (modifier = Modifier.padding(DimenTokens.Padding.normal),
                verticalArrangement = Arrangement.spacedBy(DimenTokens.Padding.normal)){
                InaIcon(data = lifeTimeReading)
                val annotatedString = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.W700
                    )){
                        append(lifeTimeReading.reading.padStart(2, '0').plus(" "))
                    }
                    append(lifeTimeReading.label)
                }
                Text(annotatedString,
                    style = MaterialTheme.typography.bodySmall)

            }

            LineChart(data,
                lineColor = 0XFF00796B.color(),
                fillColor = 0XFF4DB6AC.color(),
                modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f))

        }
    }
}

@Preview
@Composable
fun PreviewLifeTimeReadingCard(){
    LifeTimeStats(lifeTimeReading = VectorItem(
        icon = Icons.Default.FavoriteBorder,
        reading = "76",
        label = "kWh",
        color = 0XFF00796B,
    ),
        monthlyStat = VectorItem(
            icon = Icons.Default.BarChart,
            reading = "76",
            label = "kWh",
            color = 0xFFAD1457,
        ),
        costStat = VectorItem(
            icon = Icons.Default.CandlestickChart,
            reading = "76",
            label = "kWh",
            color = 0xFF00796B,
        ),
        chartData = listOf(10f, 40f, 20f, 60f, 30f, 80f, 10f, 40f, 20f, 60f, 30f, 80f),
    )
}