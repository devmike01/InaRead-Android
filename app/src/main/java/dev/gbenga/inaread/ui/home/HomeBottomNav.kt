package dev.gbenga.inaread.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.color
import dev.gbenga.inaread.ui.dashboard.DashboardScreen


@Composable
fun HomeBottomNav(modifier: Modifier = Modifier,
                  content: @Composable () -> Unit)  {

    Row(
        modifier = modifier
            .drawBehind {
                drawBottomNavBackground(0xFF263238.color())
            }
            .graphicsLayer {
                clip = false
            }
            .padding(
                horizontal = DimenTokens.Padding.small,
                vertical = DimenTokens.Padding.small
            )
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        content()
    }
}

@Composable
fun InaBottomNavItem(inaTextIcon: InaBottomNavItemData,
                     selected: (DashboardScreen) -> Boolean,
                     route: DashboardScreen,
                     onClick: (DashboardScreen) -> Unit){
    val containerColor = MaterialTheme.colorScheme.tertiary.takeIf {
        selected(route)
    } ?: Color.Transparent
    Button(
        modifier = Modifier,
        contentPadding = PaddingValues(horizontal = DimenTokens.Padding.small,
            vertical = DimenTokens.Padding.small),
        onClick = { onClick(route) },
        shape = RoundedCornerShape(DimenTokens.Radius.large),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp.takeIf {  selected(route) } ?: 0.dp
        )
        ) {

        inaTextIcon.icon?.let { icon ->
            Icon(
                imageVector = icon,
                contentDescription = inaTextIcon.label,
                modifier = Modifier
                    .size(26.dp)
                    .padding(end = DimenTokens.Padding.xSmall),
                tint = Color.White.takeIf {  selected(route) } ?: Color(0x77FFFFFF)
            )
        }

        AnimatedVisibility( selected(route)) {
            Text(inaTextIcon.label, maxLines = 1,
                style = MaterialTheme.typography.bodySmall
                    .copy(color = Color.White))
        }

    }
}

fun DrawScope.drawBottomNavBackground(background: Color){
    val corner = size.height /2
    drawRoundRect(
        background,
        cornerRadius = CornerRadius(corner)
    )
}

@Composable
@Preview
fun PreviewHomeBottomNav(){
    HomeBottomNav(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}