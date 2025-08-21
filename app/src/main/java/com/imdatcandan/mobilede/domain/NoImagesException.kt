package com.imdatcandan.mobilede.domain

class NoImagesException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)