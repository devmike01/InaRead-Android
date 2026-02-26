package dev.gbenga.inaread.data.network

object EndPoints {
   // http://inaread-v0-0-1.onrender.com/api/v1/auth/login
    const val v1 = "/api/v1"
    const val Auth = "$v1/auth"
    const val NoAuth = "$v1/noauth"
    const val Login = "$Auth/login"
    const val SignUp = "$Auth/signup"
    const val Meter = "$NoAuth/meter"
    const val MeterTypes = "$Meter/types"
    const val GetAppliances = "$v1/appliance"
    const val SetAppliance = "$GetAppliances/new/bulk"
    const val Usage = "$v1/usage"

    const val UsageMonthly = "$v1/usage/monthly"
    const val UsageYearly = "$v1/usage/yearly"

    const val GetAllUsages = "$Usage/all"
    const val RecordNewUsage = "$Usage/new"
    const val MeterType = "$Meter/types"
    const val RefreshToken = "$Auth/refresh/token"
    const val SignOut = "$Auth/logout"
    const val AllPowerUsage = "$Usage/all"
    const val Profile = "$Auth/login"
}