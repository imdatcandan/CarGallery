package com.imdatcandan.mobilede.data.model

import com.imdatcandan.mobilede.domain.CarImage
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val uri: String
)

fun ImageDto.toDomain(): CarImage {
    return CarImage(baseUri = uri)
}