package com.imdatcandan.mobilede.domain

import com.imdatcandan.mobilede.data.CarRepository

class GetCarImagesUseCaseImpl(
    private val carRepository: CarRepository
): GetCarImagesUseCase {
    override suspend operator fun invoke(carId: String): Result<List<CarImage>> {
        return try {
            val images = carRepository.getCarImages(carId)
            if (images.isEmpty()) {
                Result.failure(NoImagesException("No images found"))
            } else {
                Result.success(images)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}