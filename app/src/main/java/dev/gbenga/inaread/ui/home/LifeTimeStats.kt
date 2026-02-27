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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.gbenga.inaread.data.model.MeterMonthlyStat
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.InaCard
import dev.gbenga.inaread.ui.customs.XYAxisCenter
import java.math.BigDecimal

@Composable
fun LifeTimeStats(meterMonthlyStatData: UiData<MeterMonthlyStat>,
                  modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (lifetime, monthly, cost) = createRefs()
        when(meterMonthlyStatData){
            is UiData.Content<MeterMonthlyStat> -> {
                val meterMonthlyStat =  meterMonthlyStatData.data
                LifeTimeReadingCard(
                    meterMonthlyStat.lifeTimeReading,
                    meterMonthlyStat.chartData,
                    modifier = Modifier.constrainAs(lifetime){
                        start.linkTo(parent.start,)
                    }.fillMaxWidth(.5f)
                        .height(210.dp))

                NoChartCard(
                    meterMonthlyStat.monthlyStat,
                    Modifier.constrainAs(monthly){
                        top.linkTo(parent.top)
                        end.linkTo(parent.end,)
                        start.linkTo(lifetime.end, margin = DimenTokens.Padding.Normal)
                    })

                NoChartCard(
                    meterMonthlyStat.costStat, Modifier
                    .padding(vertical = DimenTokens.Padding.Small).constrainAs(cost){
                        end.linkTo(parent.end,)
                        start.linkTo(lifetime.end, margin = DimenTokens.Padding.Normal)
                        top.linkTo(monthly.bottom)
                    })
            }
            is UiData.EmptyContent -> {
                InaCard {
                    XYAxisCenter (
                        modifier = Modifier
                            .padding(DimenTokens.Padding.Small)
                            .height(DimenTokens.Size.emptyData)) {
                        Text("You don't have any reading for the selected month",
                            textAlign = TextAlign.Center)
                    }
                }
            }
        }

    }
}


@Composable
private fun NoChartCard(itemData: InaTextIcon,
                        modifier: Modifier = Modifier){
    InaCard(modifier = modifier) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth(.44f)
            .height(100.dp)
            .padding(start = DimenTokens.Padding.Small)) {
            val (stats, chartIcon) = createRefs()

            InaIcon(itemData, modifier = Modifier.constrainAs(chartIcon){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }.size(DimenTokens.Icon.Medium))

            Column(modifier = Modifier
                .padding(start = DimenTokens.Padding.Small)
                .constrainAs(stats){
                start.linkTo(chartIcon.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }) {
                Text(itemData.value,
                    style = MaterialTheme.typography.headlineSmall
                        .copy(fontWeight = FontWeight.W600))
                Text(itemData.label,
                    style = MaterialTheme.typography.bodySmall
                        .copy(fontWeight = FontWeight.W600),)
            }

        }
    }
}

@Composable
fun LifeTimeReadingCard(lifeTimeReading: InaTextIcon,
                        data: List<BigDecimal>,
                        modifier: Modifier = Modifier){
    InaCard(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween) {
            Column (modifier = Modifier.padding(DimenTokens.Padding.Normal),
                verticalArrangement = Arrangement.spacedBy(DimenTokens.Padding.Normal)){
                InaIcon(data = lifeTimeReading)
                val annotatedString = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.W700
                    )){
                        append(lifeTimeReading.value.padStart(2, '0').plus(" "))
                    }
                    append(lifeTimeReading.label)
                }
                Text(annotatedString,
                    style = MaterialTheme.typography.bodySmall)

            }

            LineChart(data,
                lineColor = MaterialTheme.colorScheme.tertiary,
                fillColor = Color(0x66FF5722),
                modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f))

        }
    }
}

@Preview
@Composable
fun PreviewLifeTimeReadingCard(){
    LifeTimeStats(UiData.Content(
        MeterMonthlyStat(
            lifeTimeReading = VectorInaTextIcon(
                icon = Icons.Default.FavoriteBorder,
                value = "76",
                label = "kWh",
                color = 0XFF00796B,
            ),
            monthlyStat = VectorInaTextIcon(
                icon = Icons.Default.BarChart,
                value = "76",
                label = "kWh",
                color = 0xFFAD1457,
            ),
            costStat = VectorInaTextIcon(
                icon = Icons.Default.CandlestickChart,
                value = "76",
                label = "kWh",
                color = 0xFF00796B,
            ),
            chartData = listOf(10f, 40f, 20f, 60f, 30f, 80f, 10f, 40f, 20f, 60f, 30f, 80f).map { it.toBigDecimal() },

            )
    ))
}