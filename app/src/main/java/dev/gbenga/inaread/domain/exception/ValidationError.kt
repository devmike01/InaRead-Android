package dev.gbenga.inaread.domain.exception

sealed class ValidationError(message: String) : IllegalArgumentException(message) {
    class EmptyField(field: String) : ValidationError("$field cannot be empty")
    class InvalidFormat(field: String) : ValidationError("$field has invalid format")
}
