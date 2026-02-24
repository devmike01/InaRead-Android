package dev.gbenga.inaread.ui.meterui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import dev.gbenga.inaread.R
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.validator.isName

@Composable
fun MeterFace(value: String, onValueChange: (String) -> Unit) {

    val calculatorFont = FontFamily(
        Font(R.font.obitron_bold)
    )

    TextField(
        value,
        singleLine = true,
        textStyle = MaterialTheme.typography.headlineLarge.copy(
            fontFamily = calculatorFont,
            fontSize = 45.sp,
            letterSpacing = 10.sp,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { text ->
            onValueChange(text.filter { it.isDigit() })
    }, modifier = Modifier
            .fillMaxWidth()
            .height(DimenTokens.Size.topbar)
//        .drawBehind{
//            drawRoundRect(
//                Color.Gray,
//                cornerRadius = CornerRadius(
//                    x = DimenTokens.Radius.small.value,
//                    y = DimenTokens.Radius.small.value)
//                )
//        }
    )
}