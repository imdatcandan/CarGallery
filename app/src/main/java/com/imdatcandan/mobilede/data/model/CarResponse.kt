package com.imdatcandan.mobilede.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CarResponse(
    val images: List<Image> = emptyList(),
)
