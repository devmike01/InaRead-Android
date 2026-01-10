package dev.gbenga.inaread.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LineChart(
    data: List<Float>,
    modifier: Modifier = Modifier,
    lineColor: Color = Color.Blue,
    fillColor: Color = Color.Blue.copy(alpha = 0.3f)
) {
    Canvas(modifier = modifier) {

        if (data.isEmpty()) return@Canvas

        val maxY = data.maxOrNull() ?: 0f
        val minY = data.minOrNull()  ?: 0f
        val range = maxY - minY

        val stepX = size.width / (data.size - 1)

        val linePath = Path()
        val fillPath = Path()

        data.forEachIndexed { index, value ->
            val x = stepX * index
            val y = size.height - ((value - minY) / range) * size.height

            if (index == 0) {
                linePath.moveTo(x, y)
                fillPath.moveTo(x, size.height)
                fillPath.lineTo(x, y)
            } else {
                linePath.lineTo(x, y)
                fillPath.lineTo(x, y)
            }
        }

        // Close the fill path to the bottom
        fillPath.lineTo(size.width, size.height)
        fillPath.close()

        // Draw filled area
        drawPath(
            path = fillPath,
            color = fillColor
        )

        // Draw line on top
        drawPath(
            path = linePath,
            color = lineColor,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}

@Preview
@Composable
fun PreviewLineChart(){
    LineChart(
        data = listOf(10f, 40f, 20f, 60f, 30f, 80f, 10f, 40f, 20f, 60f, 30f, 80f),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )

}