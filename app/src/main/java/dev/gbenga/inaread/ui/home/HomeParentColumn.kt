package dev.gbenga.inaread.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.HorizontalCenter

@Composable
fun HomeParentColumn(title: String,
                     subTitle: String? =null,
                     content: @Composable () -> Unit) {
    HorizontalCenter(
        modifier = Modifier
            .padding(DimenTokens.Padding.large)
            .fillMaxSize()
    ) {
        val (titleFont, titleWeight) = MaterialTheme.typography.headlineLarge.let {
            Pair(it.fontSize, it.fontWeight)
        }
        val titleSubTitle = buildAnnotatedString {
            withStyle(style = SpanStyle(
                fontSize = titleFont,
                fontWeight = titleWeight
            )){
                append(title)
            }
            subTitle?.let {
                append(subTitle)
            }
        }
        Text(titleSubTitle, style = MaterialTheme.typography
            .titleLarge.copy(fontWeight = FontWeight.W400),
            textAlign = TextAlign.Center)
        content()
    }
}