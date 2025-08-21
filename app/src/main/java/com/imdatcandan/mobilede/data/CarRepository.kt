package com.imdatcandan.mobilede.data

import com.imdatcandan.mobilede.domain.CarImage

interface CarRepository {
    suspend fun getCarImages(carId: String): List<CarImage>
}