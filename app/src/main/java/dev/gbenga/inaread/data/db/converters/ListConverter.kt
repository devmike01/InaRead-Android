package dev.gbenga.inaread.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal

class ListConverter() {

    private val gson: Gson = Gson()

    @TypeConverter
    fun listToString(list: List<BigDecimal>): String{
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToList(listStr: String): List<BigDecimal>{
        val type = object : TypeToken<List<BigDecimal>>(){}.type
        return gson.fromJson(listStr, type)
    }
}