package dev.gbenga.inaread.ui.validator

import android.util.Log
import dev.gbenga.inaread.domain.exception.ValidationError
import dev.gbenga.inaread.tokens.StringTokens
import javax.inject.Inject

class AuthFieldsValidator @Inject constructor() {

    fun validateUsername(username: String): Result<String> {

       return when {
            username.isBlank() ->
                Result.failure(ValidationError.EmptyField("Username"))
            !username.isUsername() ->
                Result.failure(ValidationError.InvalidFormat("Email"))
            else -> Result.success(username)
        }

    }

    fun validateEmail(email: String): Result<String> {
        Log.i("enableSubmit02", "${email.isEmail()}: ")

       return if (email.isEmail()){
           Result.success(email)
       }else{
           Result.failure(ValidationError.InvalidFormat("Email"))
       }
    }

    fun validateNumberOnly(number: String): Result<String>{
        return when {
            number.isBlank() -> Result.failure(ValidationError
                .EmptyField("Meter Number"))
            !number.isUsername() -> Result.failure(ValidationError.InvalidFormat("Meter Number"))
            else -> Result.success(number)
        }
    }

    fun validatePassword(password: String): Result<String>{
        return when {
            password.isBlank() ->
                Result.failure(ValidationError.EmptyField("Password"))
            !password.isPassword() ->
                Result.failure(ValidationError.InvalidFormat("Password"))
            else -> Result.success(password)
        }
    }

    fun <T1, T2> combine(result1: Result<T1>,
                         result2: Result<T2>,
                         onError: (String) -> Unit,
                         onValidate: (T1, T2) -> Unit){

        val result =  result1.map { t1 ->
            result2.map { t2 ->
                Pair(t1, t2)
            }.getOrThrow()
        }.fold(
            onSuccess = { (t1, t2) ->
                onValidate(t1, t2)
            },
            onFailure = {
                onError(it.message ?: StringTokens.UnknownErrorOccured)
            }
        )

        return result
    }
}


internal fun String.isUsername(): Boolean{
    if (this.length < 2) return false
    return this.all { it.isLetterOrDigit() }
}

internal fun String.isName(): Boolean{
    if (this.length < 2) return false
    return all { it.isLetter() }
}

fun String.isPassword(): Boolean {
    return true
}

// michae123_#d@gma1l.com
internal fun String.isEmail(): Boolean{
    val segments = split("@")
    if (segments.size != 2){
        return false
    }
    val isValidUsername = segments[0].isUsername()
    val nameAndDomain = segments[1].split(".")
    if (nameAndDomain.size < 2) return false
    val isDomainName = nameAndDomain[0].isUsername()
    val isDomain = nameAndDomain[1].isName()
    return isValidUsername && isDomainName && isDomain
}