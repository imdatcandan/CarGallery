package com.imdatcandan.mobilede.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CarResponseDto(
    val images: List<ImageDto> = emptyList(),
)

