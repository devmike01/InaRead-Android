package dev.gbenga.inaread.ui.customs

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource


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