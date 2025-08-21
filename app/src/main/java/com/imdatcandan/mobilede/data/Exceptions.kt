package com.imdatcandan.mobilede.data

class ApiException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)

class NetworkException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)