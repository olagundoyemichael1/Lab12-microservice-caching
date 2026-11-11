// Lab 12
package com.example.models

data class ValidationError(val field: String, val message: String)

sealed class ServiceResult<out T> {
    data class Success<T>(val data: T) : ServiceResult<T>()
    data class ValidationFailure(val errors: List<ValidationError>) : ServiceResult<Nothing>()
    data class NotFound(val message: String) : ServiceResult<Nothing>()
    data class Error(val message: String) : ServiceResult<Nothing>()
}