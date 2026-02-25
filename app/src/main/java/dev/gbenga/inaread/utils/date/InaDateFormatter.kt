package dev.gbenga.inaread.utils.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class InaDateFormatter @Inject constructor(private val sdf: SimpleDateFormat) {

    fun ddMMM(dateInMillis: Long): String{
        sdf.applyPattern("dd/MMM")
        return sdf.format(dateInMillis)
    }

    fun ddMMM(dateStr: String): String{
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
        val parsedDate = sdf.parse(dateStr)
        sdf.applyPattern("dd/MMM")
        return parsedDate?.let { sdf.format(it) } ?: ""
    }

    fun ddMMM(date: Date): String{
        sdf.applyPattern("dd/MMM")
        return sdf.format(date)
    }

    fun dddMMMYyyy(date: Date): String{
        sdf.applyPattern("EEE, MMM d, yyyy")
        // Min, Feb 20, 2021
        return sdf.format(date)
    }


    fun dddMMMYyyyMhs(date: Date): String{
        sdf.applyPattern("EEE-MMM-d-yyyy_HH:mm:ss")
        return sdf.format(date)
    }

    fun yyyyMMDD(date: Date): String{
        return with(sdf){
            applyPattern("yyyy-MM-dd")
            format(date)
        }
    }
    
}