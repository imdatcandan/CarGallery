package com.imdatcandan.mobilede.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdatcandan.mobilede.data.CarRepository
import com.imdatcandan.mobilede.data.model.Image
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CarListViewModel(private val carRepository: CarRepository) : ViewModel() {

    val state: StateFlow<UiState<List<Image>>> = flow{
        emit(UiState.Loading)
        try {
           val images = carRepository.getCarDetails()
            emit(UiState.Success(images))
        }catch (e: Exception){
            emit(UiState.Error(e.message ?: "Unknown error"))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)
}
