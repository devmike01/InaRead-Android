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

}