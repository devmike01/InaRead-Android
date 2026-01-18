package dev.gbenga.inaread.ui.customs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.theme.Indigo600


data class InaTextFieldConfig(
    val enabled: Boolean = true,
    val label: String? = null,
    val trailingIcon: IconConfig? = null,
    val leadingIcon: IconConfig? = null,
    val singleLine: Boolean = true,
    val placeholder: String? = null
)


@Composable
fun InaTextField(value: String,
                 config: InaTextFieldConfig = InaTextFieldConfig(),
                 onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        enabled = config.enabled,
        label = {
            config.label.NullableText()
        },
        placeholder = {
            config.placeholder.NullableText()
        },
        singleLine = config.singleLine,
        trailingIcon = {
            config.trailingIcon.NullableIcon()
        },
        leadingIcon = {
            config.leadingIcon.NullableIcon()
        },
        )
}



@Composable
fun InaSingleTextField(
    modifier: Modifier =Modifier,
    value: String,placeholder: String,
    onValueChange: (String) -> Unit,){
    TextField(value,
        modifier = modifier
            .height(DimenTokens.Auth.TextFieldHeight)
            .padding(vertical = DimenTokens.Padding.small)
            .fillMaxWidth(),
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(placeholder)
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Indigo600,
            focusedContainerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(DimenTokens.Radius.large)
    )
}