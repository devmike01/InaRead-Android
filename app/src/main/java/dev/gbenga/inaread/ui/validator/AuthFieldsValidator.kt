package dev.gbenga.inaread.ui.validator

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
       return when {
            email.isBlank() ->
                Result.failure(ValidationError.EmptyField("Email"))
            !email.isUsername() ->
                Result.failure(ValidationError.InvalidFormat("Email"))
            else -> Result.success(email)
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

    return this.all { it.isLetterOrDigit() }
}

internal fun String.isName(): Boolean{
    return all { it.isLetter() }
}

fun String.isPassword(): Boolean {
    return true
}

// michae123_#d@gma1l.com
internal fun String.isEmail(): Boolean{
    val segments = split("@")
    if (segments.size != 3){
        return false
    }
    val isInValidUsername = segments[0].length > 1
    val isNotDomainName = segments[1].any { !it.isLetterOrDigit() }
    val isNotDomain = segments[2].any { !it.isLetter() }
    return !(isInValidUsername || isNotDomainName || isNotDomain)
}