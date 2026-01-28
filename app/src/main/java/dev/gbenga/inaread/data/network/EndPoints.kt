package dev.gbenga.inaread.data.network

object EndPoints {

    const val Auth = "/auth"
    const val NoAuth = "/noauth"
    const val Login = "$Auth/login"
    const val SignUp = "$Auth/signup"
    const val Meter = "$NoAuth/meter"
    const val MeterTypes = "$Meter/types"

}