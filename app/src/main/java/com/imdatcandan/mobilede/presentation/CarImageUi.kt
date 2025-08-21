package com.imdatcandan.mobilede.presentation

import com.imdatcandan.mobilede.domain.CarImage

data class CarImageUi(
    val thumbnailUrl: String,
    val largeUrl: String
)

fun CarImage.toUi(): CarImageUi {
    val cleanUri = baseUri.removePrefix("m.mobile.de/yams-proxy/")
    return CarImageUi(
        thumbnailUrl = "https://$cleanUri?rule=mo-640.jpg",
        largeUrl = "https://$cleanUri?rule=mo-1600.jpg"
    )
}