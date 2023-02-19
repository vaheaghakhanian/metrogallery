package com.ramonapp.metgallery.domain.model

sealed class ErrorType {
    object NetworkError: ErrorType()
    object ClientError: ErrorType()
    object ServerError: ErrorType()
    object ParseError: ErrorType()
    object UnExpectedError: ErrorType()
}
