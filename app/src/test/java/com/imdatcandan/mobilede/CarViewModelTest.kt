package com.imdatcandan.mobilede

import app.cash.turbine.test
import com.imdatcandan.mobilede.data.CarRepository
import com.imdatcandan.mobilede.data.model.Image
import com.imdatcandan.mobilede.view.CarListViewModel
import com.imdatcandan.mobilede.view.UiState
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

@ExperimentalCoroutinesApi
    class CarListViewModelTest {

        private val testDispatcher = StandardTestDispatcher()

        private lateinit var carRepository: CarRepository
        private lateinit var viewModel: CarListViewModel

        @Before
        fun setup() {
            Dispatchers.setMain(testDispatcher)
            carRepository = mockk()
        }

        @After
        fun tearDown() {
            Dispatchers.resetMain()
        }

        @Test
        fun `state emits Loading then Success`() = runTest {
            // Arrange
            val images = listOf(
                Image("img1"),
                Image("img2")
            )
            coEvery { carRepository.getCarDetails() } returns images

            // Act
            viewModel = CarListViewModel(carRepository)

            // Assert
            viewModel.state.test {
                assertEquals(UiState.Loading, awaitItem())
                assertEquals(UiState.Success(images), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

        @Test
        fun `state emits Loading then Error`() = runTest {
            // Arrange
            coEvery { carRepository.getCarDetails() } throws RuntimeException("Network failed")

            // Act
            viewModel = CarListViewModel(carRepository)

            // Assert
            viewModel.state.test {
                assertEquals(UiState.Loading, awaitItem())
                val errorState = awaitItem()
                assert(errorState is UiState.Error)
                assertEquals("Network failed", (errorState as UiState.Error).message)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
