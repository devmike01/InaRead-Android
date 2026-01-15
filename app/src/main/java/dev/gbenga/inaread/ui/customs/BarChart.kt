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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gbenga.inaread.data.model.MonthValue
import dev.gbenga.inaread.ui.theme.DeepOrange

@Composable
fun BarChart(
    data: List<MonthValue>,
    modifier: Modifier = Modifier,
    barColor: Color = DeepOrange
) {
    val maxValue = data.maxOf { it.value }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(16.dp)
    ) {
        val barWidth = size.width / (data.size * 1.5f)
        val space = barWidth / 2

        data.forEachIndexed { index, item ->
            val barHeight = (item.value / maxValue) * size.height

            val x = index * (barWidth + space)
            val y = size.height - barHeight

            drawRect(
                color = barColor.takeIf { index % 2 ==0 } ?: 0xFF263238.color() ,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight)
            )
        }
    }
}

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
