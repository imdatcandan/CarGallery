package com.imdatcandan.mobilede.presentation

sealed class UiError(val message: String) {
    object Network : UiError("Network error, please try again.")
    object Server : UiError("Server error, please try later.")
    object NoImages : UiError("No images available for this car.")
    data class Unknown(val details: String?) : UiError("Unexpected error occurred: $details")
}