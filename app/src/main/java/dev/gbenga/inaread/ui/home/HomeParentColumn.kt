package dev.gbenga.inaread.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.HorizontalCenter
import dev.gbenga.inaread.ui.theme.Indigo50
import dev.gbenga.inaread.ui.theme.White

@Composable
fun HomeParentColumn(title: String,
                     subTitle: String? =null,
                     modifier: Modifier =Modifier,
                     content: @Composable () -> Unit) {
    HorizontalCenter(
        modifier = modifier
            .padding(DimenTokens.Padding.Large)
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
        Text(titleSubTitle, style = MaterialTheme.typography
            .bodyMedium.copy(fontWeight = FontWeight.W400,
                color = Indigo50,),
            textAlign = TextAlign.Center)
        content()
    }
}