package dev.gbenga.inaread.utils.ext

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.naira() : String{
    return this.setScale(2, RoundingMode.HALF_EVEN)
        .money()
        .let { "\u20A6$it" }
}

fun BigDecimal.money(): String{
    val format = NumberFormat.getNumberInstance(Locale("en", "NG"))
    format.minimumIntegerDigits = 0
    format.maximumFractionDigits = 2

    return format.format(this)
}

