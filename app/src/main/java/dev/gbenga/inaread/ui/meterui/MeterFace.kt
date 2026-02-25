package dev.gbenga.inaread.ui.meterui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import dev.gbenga.inaread.R
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.theme.Indigo200
import dev.gbenga.inaread.ui.theme.White

@Composable
fun MeterFace(value: String, onValueChange: (String) -> Unit) {

    val calculatorFont = FontFamily(
        Font(R.font.obitron_bold)
    )

    val decimalRegex = remember { Regex("^\\d*\\.?\\d{0,2}$") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement
            .spacedBy(DimenTokens.Padding.Large)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(DimenTokens.Size.topbar)) {

            OutlinedTextField(
                value,
                singleLine = true,
                placeholder = {
                    Text("000.00", style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = calculatorFont,
                        fontSize = 45.sp,
                        letterSpacing = 10.sp,
                        color = Color.Black.copy(alpha = .3f)
                    ))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = White
                ),
                textStyle = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = calculatorFont,
                    fontSize = 45.sp,
                    letterSpacing = 10.sp,
                    color = Color.Black.copy(alpha = .8f)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = { input ->
                    val formattedInp = input.toDoubleOrNull()?.let {
                        "%.2f".format(it)
                    }

                    if (formattedInp != null && decimalRegex.matches(formattedInp)) {
                        onValueChange(formattedInp)
                    }
                }, modifier = Modifier
                    .fillMaxSize()
                    .drawBehind{
                        drawRoundRect(
                            Indigo200,
                            cornerRadius = CornerRadius(x = DimenTokens
                                .Radius.small.value,
                                y = DimenTokens.Radius.small.value)
                        )
                    }

            )

            Text("KWh",
                modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(horizontal = DimenTokens.Padding.XSmall),
                style = MaterialTheme.typography.titleMedium
                    .copy(fontFamily = calculatorFont,
                        color = Color.Black.copy(alpha = .5f)))
        }

    }
}