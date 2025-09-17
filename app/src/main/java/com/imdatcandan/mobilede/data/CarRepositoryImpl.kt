package com.imdatcandan.mobilede.data

import com.imdatcandan.mobilede.data.model.toDomain
import com.imdatcandan.mobilede.domain.CarImage
import retrofit2.HttpException
import java.io.IOException

class CarRepositoryImpl(
    private val api: CarApiService
) : CarRepository {
    override suspend fun getCarImages(carId: String): List<CarImage> {
        try {
            val response = api.getCarDetails(carId)
            return response.images.map { it.toDomain() }
        } catch (e: IOException) {
            throw NetworkException("Network error", e)
        } catch (e: HttpException) {
            throw ApiException("API error ${e.code()}", e)
        }
    }
}