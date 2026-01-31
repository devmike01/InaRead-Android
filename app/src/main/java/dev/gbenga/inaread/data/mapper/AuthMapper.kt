package dev.gbenga.inaread.data.mapper

import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpResponse
import dev.gbenga.inaread.data.db.entities.UserEntity
import dev.gbenga.inaread.data.model.LoginInput
import dev.gbenga.inaread.data.model.LoginOutput
import dev.gbenga.inaread.data.model.SignUpOutput

fun LoginRequest.toLoginInput(): LoginInput{
    return LoginInput(
        username,
        password
    )
}

fun LoginInput.toLoginRequest() : LoginRequest {
    return LoginRequest(
        username,
        password = password
    )
}

fun LoginResponse.toLoginOutput(): LoginOutput{
    return LoginOutput(
        username,
        meterNo,
        countryId,
        meterCategoryId,
        createdAt,
        enabled,
        username,
        email
    )
}

fun UserEntity.toLoginOutput(): LoginOutput{
    return LoginOutput(
        customerId,
        meterNo,
        countryId,
        meterCategoryId,//
        createdAt,
        enabled,
        username,
        email
    )
}

fun LoginResponse.toUserEntity(): UserEntity{
    return UserEntity(
        customerId,
        username,
        email,
        meterNo,
        countryId,
        meterCategoryId,
        createdAt,
        enabled,
        locked = false
    )
}

fun SignUpResponse.toSignUpOutput(): SignUpOutput {
    return SignUpOutput(
        message = this.message
    )
}