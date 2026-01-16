package dev.gbenga.inaread.ui.customs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gbenga.inaread.data.model.MonthValue
import dev.gbenga.inaread.ui.theme.DeepOrange
import dev.gbenga.inaread.ui.theme.White

@Composable
fun BarChart(
    data: List<MonthValue>,
    modifier: Modifier = Modifier,
    barColor: Color = DeepOrange
) {
    val maxValue = (data.maxOfOrNull { it.value } ?: 1f).coerceAtLeast(1f)
    val steps = 5

    val textMeasurer = rememberTextMeasurer()

    val labelStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = White
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(8.dp)
    ) {

        val yAxisWidth = 36.dp.toPx()
        val xAxisHeight = 24.dp.toPx()

        val chartWidth = size.width - yAxisWidth
        val chartHeight = size.height - xAxisHeight


        drawLine(
            color = White,
            start = Offset(yAxisWidth, 0f),
            end = Offset(yAxisWidth, chartHeight),
            strokeWidth = 2f
        )

        for (i in 0..steps) {
            val value = maxValue * i / steps
            val y = chartHeight - (chartHeight * i / steps)

            // grid line
            drawLine(
                color = White.copy(alpha = 0.2f),
                start = Offset(yAxisWidth, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )

            // Y-axis labels (NO NEGATIVE X!)
            drawText(
                textMeasurer = textMeasurer,
                text = AnnotatedString(value.toInt().toString()),
                style = labelStyle,
                topLeft = Offset(
                    4.dp.toPx(),
                    y - 6.dp.toPx()
                )
            )
        }

        drawLine(
            color = White,
            start = Offset(yAxisWidth, chartHeight),
            end = Offset(size.width, chartHeight),
            strokeWidth = 2f
        )

        val barWidth = chartWidth / (data.size * 1.5f)
        val space = barWidth / 2

        data.forEachIndexed { index, item ->
            val barHeight = (item.value / maxValue) * chartHeight

            val x = yAxisWidth + index * (barWidth + space) + space
            val y = chartHeight - barHeight

            drawRect(
                color = if (index % 2 == 0) barColor else Color(0xFF263238),
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight)
            )

            // Month labels (bottom)
            drawText(
                textMeasurer = textMeasurer,
                text = AnnotatedString(item.month),
                style = labelStyle,
                topLeft = Offset(
                    x,
                    chartHeight + 4.dp.toPx()
                )
            )
        }
    }
}


//@Composable
//fun <T: Comparable<T>> List<T>.biggestOf(selector: (T) -> Float) : Float{
//    var max: Float = selector(this.firstOrNull() ?: throw NullPointerException("")) // this.firstOrNull() ?: throw NullPointerException("")
//    for (s in this){
//        max = maxOf(s, max)
//    }
//    return max as R
//}

@Preview
@Composable
fun PreviewBarChart(){
    val monthlyData = listOf(
        "Jan","Feb","Mar","Apr","May","Jun",
        "Jul","Aug","Sep","Oct","Nov","Dec"
    ).map {
        MonthValue(it, (10..100).random().toFloat())
    }
    BarChart(monthlyData)
}
