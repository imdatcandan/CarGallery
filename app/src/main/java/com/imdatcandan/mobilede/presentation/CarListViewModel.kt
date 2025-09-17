package com.imdatcandan.mobilede.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdatcandan.mobilede.domain.CarImage
import com.imdatcandan.mobilede.domain.GetCarImagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.map

class CarListViewModel(
    private val getCarImagesUseCase: GetCarImagesUseCase,
    private val errorMapper: ErrorMapper
) : ViewModel() {


    private val _state = MutableStateFlow<UiState<List<CarImageUi>>>(UiState.Loading)
    val state: StateFlow<UiState<List<CarImageUi>>> = _state

    init {
        loadCarImages("285041801")
    }

    private fun loadCarImages(carId: String) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val result: Result<List<CarImage>> = getCarImagesUseCase.invoke(carId)
                result.fold(
                    onSuccess = { images ->
                        val uiImages = images.map { it.toUi() }
                        _state.value = UiState.Success(uiImages)
                    },
                    onFailure = { throwable ->
                        val uiError = errorMapper.map(throwable)
                        _state.value = UiState.Error(uiError.message)
                    }
                )
            } catch (e: Exception) {
                _state.value = UiState.Error("Unexpected error occurred: ${e.message}")
            }
        }
    }

    fun retry() {
        loadCarImages("285041801")
    }
}