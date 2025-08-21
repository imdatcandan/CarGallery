package com.imdatcandan.mobilede.data

import com.imdatcandan.mobilede.data.model.CarResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CarApiService {
    @GET("svc/a/{id}")
    suspend fun getCarDetails(@Path("id") id: String): CarResponseDto
}