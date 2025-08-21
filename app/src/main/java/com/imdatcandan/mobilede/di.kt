package com.imdatcandan.mobilede

import com.imdatcandan.mobilede.data.CarRepository
import com.imdatcandan.mobilede.data.CarRepositoryImpl
import com.imdatcandan.mobilede.data.CarApiService
import com.imdatcandan.mobilede.domain.GetCarImagesUseCase
import com.imdatcandan.mobilede.domain.GetCarImagesUseCaseImpl
import com.imdatcandan.mobilede.presentation.CarListViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    single<CarApiService> {
        get<Retrofit>().create(CarApiService::class.java)
    }

    single<CarRepository> { CarRepositoryImpl(get()) }

    single<GetCarImagesUseCase> { GetCarImagesUseCaseImpl(get()) }

    viewModel { CarListViewModel(get()) }

    single {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()



        Retrofit.Builder()
            .baseUrl("https://api.mobile.de/")
            .client(get())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(logging) // Add logging before header interceptor
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("User-Agent", "de.mobile.android.app/9.0 (1000) (gzip)")
                    .addHeader("X-Mobile-Api-Version", "10")
                    .addHeader("Accept", "application/json")
                    .addHeader("Accept-Language", "de-DE")
                    .addHeader("Connection", "Keep-Alive")
                    .build()
                chain.proceed(request)
            }
            .build()
    }




}