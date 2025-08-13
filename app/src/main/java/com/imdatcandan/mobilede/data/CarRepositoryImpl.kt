package com.imdatcandan.mobilede.data

import com.imdatcandan.mobilede.data.model.Image

class CarRepositoryImpl(private val carApiService: CarApiService) : CarRepository {
    override suspend fun getCarDetails(): List<Image> {
        return carApiService.getCarDetails("285041801").images
    }
}