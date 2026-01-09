package dev.gbenga.inaread.ui.customs

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource


@Composable
fun String?.NullableText(){
    this?.let {
        Text(it)
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
