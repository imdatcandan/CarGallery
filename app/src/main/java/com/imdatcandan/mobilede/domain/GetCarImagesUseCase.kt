package com.imdatcandan.mobilede.domain

interface GetCarImagesUseCase {
    suspend operator fun invoke(carId: String): Result<List<CarImage>>
}