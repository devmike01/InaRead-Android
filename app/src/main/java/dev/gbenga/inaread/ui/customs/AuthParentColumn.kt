package dev.gbenga.inaread.ui.customs

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.theme.Indigo50
import dev.gbenga.inaread.ui.theme.Indigo600
import dev.gbenga.inaread.ui.theme.Indigo700
import dev.gbenga.inaread.ui.theme.Indigo800
import dev.gbenga.inaread.ui.theme.White

@Composable
fun AuthParentColumn(title: String,
                     subTitle: String? =null,
                     modifier: Modifier =Modifier,
                     content: @Composable BoxScope.() -> Unit) {

    val background = MaterialTheme.colorScheme.background

    Box(modifier = Modifier.fillMaxSize()) {
        AuthTitledColumn(title = title,
            subTitle = subTitle,
            modifier = modifier
                .drawBehind{
                    val colors = listOf(Indigo600,
                        Indigo700, Indigo800
                    )
                    // The brush is recreated on each animation frame
                    val brush = Brush.radialGradient(
                        colors = colors,
                    )

                    drawRect(color = background, size = size)
                }.fillMaxSize()
                .padding(DimenTokens.Padding.normal)
                .align(Alignment.Center)
        ) {
            content()
        }
    }
}


@Composable
fun AuthTitledColumn(
    modifier: Modifier =Modifier,
    title: String,
                     subTitle: String? =null,
                     content: @Composable () -> Unit) {
    Column (
        modifier = modifier
            .padding(vertical = DimenTokens.Padding.large)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val (titleFont, titleWeight) = MaterialTheme.typography.headlineLarge.let {
            Pair(it.fontSize, it.fontWeight)
        }
        val titleSubTitle = buildAnnotatedString {
            withStyle(style = SpanStyle(
                fontSize = titleFont,
                fontWeight = titleWeight,
                color = White,
            )){
                append("$title\n\n")
            }
            subTitle?.let {
                append(subTitle)
            }
        }
        Text(titleSubTitle,
            style = MaterialTheme.typography
            .bodyMedium.copy(fontWeight = FontWeight.W400,
                color = Indigo50),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = DimenTokens.Padding.normal))
        content()
    }
}