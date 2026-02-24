package dev.gbenga.inaread.ui.meterui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import dev.gbenga.inaread.ui.addreading.DatePickerDocked
import dev.gbenga.inaread.ui.theme.Indigo200
import dev.gbenga.inaread.ui.theme.Indigo400
import java.text.DecimalFormat

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
                    focusedBorderColor = Indigo400
                ),
                textStyle = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = calculatorFont,
                    fontSize = 45.sp,
                    letterSpacing = 10.sp,
                    color = Color.Black.copy(alpha = .8f)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = { input ->
                    val d = DecimalFormat("0.00")
                    if (decimalRegex.matches(input)) {
                        onValueChange(input)
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