package dev.gbenga.inaread.ui.signup

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.theme.Indigo400
import dev.gbenga.inaread.ui.theme.Indigo600

@Composable
fun MeterSubscriptionType(selection: String,
                          meterTypes: List<String>,
                          onSelect: (String) -> Unit) {

    LazyRow(
        modifier = Modifier.height(DimenTokens.Button.Normal),
        horizontalArrangement = Arrangement.spacedBy(DimenTokens.Padding.XSmall)
    ) {
        items(meterTypes.size,) {
            MeterSubscriptionTypeItem(
                selection,
                meterTypes[it],
                onSelect
            )
        }
    }
}



@Composable
fun MeterSubscriptionTypeItem(
    selection: String,
    meterType: String,
    onSelect: (String) -> Unit){
    OutlinedButton(onClick = {
        onSelect(meterType)
    }, colors = ButtonDefaults.outlinedButtonColors(
        containerColor = MaterialTheme
            .colorScheme.primary.takeIf { meterType == selection }
            ?: Color.Transparent
    ), border = BorderStroke(1.dp,
        MaterialTheme
            .colorScheme.primary.takeIf { selection == meterType }
            ?: Color.Gray)) {
        Text(meterType)
    }
}

@Composable
fun HorizontalLoading(count: Int){
    val transition = rememberInfiniteTransition(label = "shimmer")
    val density = LocalDensity.current
    val progressAnimated by transition.animateFloat(
        initialValue = with(density){
            -(DimenTokens.Size.LoadingHorizontal.toPx() * 2)
        },
        targetValue = with(density){
            DimenTokens.Size.LoadingHorizontal.toPx() * 2
        },
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "shimmer"
    )


    Row(modifier = Modifier.fillMaxWidth()
        .height(DimenTokens.Size.LoadingHorizontal)) {
        for (i in 1..count){
            Box(modifier = Modifier.padding(DimenTokens.Padding.XSmall)) {
                Box(modifier = Modifier.clip(RoundedCornerShape(
                    DimenTokens.Size.LoadingHorizontal
                )).drawBehind{
                    val brush = Brush.linearGradient(listOf(Indigo400,
                        Indigo600, Indigo400),
                        start = Offset(0F, 0F),
                        end = Offset(progressAnimated + DimenTokens.Size.LoadingHorizontal.toPx(),
                            80.dp.toPx())
                    )
                    drawRoundRect(brush)
                }.height(DimenTokens.Size.LoadingHorizontal)
                    .width(80.dp), content = {})
            }
        }
    }
}