package dev.gbenga.inaread.utils.date

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class InaDateFormatter @Inject constructor(private val sdf: SimpleDateFormat,
                                           private val formatter: DateTimeFormatter) {

    fun ddMMM(dateInMillis: Long): String{
        sdf.applyPattern("dd/MMM")
        return sdf.format(dateInMillis)
    }

    fun mMM(dateStr: String): String?{
        val date = formatter.parse(dateStr, Instant::from)
        sdf.applyPattern("MMM")
        return date?.let { sdf.format(it.toEpochMilli()) }
    }

    fun ddMMM(dateStr: String): String{
        val parsedDate = formatter.parse(dateStr, Instant::from)
        sdf.applyPattern("dd/MMM")
        return parsedDate?.let { sdf.format(it.toEpochMilli()) } ?: ""
    }

    fun ddMMM(date: Date): String{
        sdf.applyPattern("dd/MMM")
        return sdf.format(date)
    }


    fun dddMMMYyyy(dateStr: String?): String{
        val parsedDate = formatter.parse(dateStr, Instant::from)
        sdf.applyPattern("EEE, MMM d, yyyy")
        // Min, Feb 20, 2021
        return sdf.format(parsedDate.toEpochMilli())
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