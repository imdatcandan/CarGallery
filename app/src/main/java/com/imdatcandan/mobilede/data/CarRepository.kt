package com.imdatcandan.mobilede.data

import com.imdatcandan.mobilede.data.model.Image

interface CarRepository {
    suspend fun getCarDetails(): List<Image>
}