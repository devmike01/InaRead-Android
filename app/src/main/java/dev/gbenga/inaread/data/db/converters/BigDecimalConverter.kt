package dev.gbenga.inaread.data.db.converters

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.math.RoundingMode

class BigDecimalConverter {

    @TypeConverter
    fun bigDecimalToDouble(value: BigDecimal): String{
        return value
            .setScale(2, RoundingMode.HALF_EVEN)
            .toPlainString()
    }

    @TypeConverter
    fun stringToBigDecimal(value: String): BigDecimal{
        return value.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)
    }
}