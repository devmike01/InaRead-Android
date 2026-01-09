package dev.gbenga.inaread.ui.customs

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle


@Composable
fun String?.NullableText(style: TextStyle= LocalTextStyle.current){
    this?.let {
        Text(it, style = style)
    }
}

@Composable
fun IconConfig?.NullableIcon(){
    this?.let {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = description
        )
    }
}


data class IconConfig(@param:DrawableRes val iconResId: Int, val description: String?)
