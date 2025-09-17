package com.imdatcandan.mobilede.presentation

import com.imdatcandan.mobilede.data.ApiException
import com.imdatcandan.mobilede.data.NetworkException
import com.imdatcandan.mobilede.domain.NoImagesException

class ErrorMapper {
    fun map(throwable: Throwable): UiError {
        return when (throwable) {
            is NetworkException -> UiError.Network
            is ApiException -> UiError.Server
            is NoImagesException -> UiError.NoImages
            else -> UiError.Unknown(throwable.message)
        }
    }
}