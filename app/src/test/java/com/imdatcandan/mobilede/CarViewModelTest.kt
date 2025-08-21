package com.imdatcandan.mobilede

import app.cash.turbine.test
import com.imdatcandan.mobilede.data.CarRepository
import com.imdatcandan.mobilede.domain.CarImage
import com.imdatcandan.mobilede.domain.GetCarImagesUseCase
import com.imdatcandan.mobilede.presentation.CarImageUi
import com.imdatcandan.mobilede.presentation.CarListViewModel
import com.imdatcandan.mobilede.presentation.UiState
import com.imdatcandan.mobilede.presentation.toUi
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.imdatcandan.mobilede.data.NetworkException

@OptIn(ExperimentalCoroutinesApi::class)
class CarListViewModelTest {

    private lateinit var getCarImagesUseCase: GetCarImagesUseCase
    private lateinit var viewModel: CarListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCarImagesUseCase = mockk()
    }

    @Test
    fun `state emits Success when use case returns images`() = runTest {
        val carImages = listOf(
            CarImage( "url1"),
            CarImage( "url2")
        )

        coEvery { getCarImagesUseCase.invoke(any())} returns (Result.success(carImages))

        viewModel = CarListViewModel(getCarImagesUseCase)

        viewModel.state.test {
            assertEquals(UiState.Loading, awaitItem())
            val successState = awaitItem()
            assert(successState is UiState.Success)
            val images = (successState as UiState.Success).data
            assertEquals(2, images.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state emits Error when use case returns failure`() = runTest {
        val exception = NetworkException("No internet")
        coEvery { getCarImagesUseCase.invoke(any())} returns (Result.failure(exception))

        viewModel = CarListViewModel(getCarImagesUseCase)

        viewModel.state.test {
            assertEquals(UiState.Loading, awaitItem())
            val errorState = awaitItem()
            assert(errorState is UiState.Error)
            assertEquals("Network error, please try again.", (errorState as UiState.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
