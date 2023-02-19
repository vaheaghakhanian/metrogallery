package com.ramonapp.metgallery.util

import androidx.annotation.StringRes
import com.ramonapp.metgallery.app.R
import com.ramonapp.metgallery.domain.model.ErrorType


object ErrorMapper {

    @StringRes
    fun map(errorType: ErrorType): Int {
        return when (errorType) {
            ErrorType.ClientError -> R.string.client_error
            ErrorType.NetworkError -> R.string.network_error
            ErrorType.ParseError -> R.string.parse_error
            ErrorType.ServerError -> R.string.server_error
            ErrorType.UnExpectedError -> R.string.unexpected_error
        }
    }
}