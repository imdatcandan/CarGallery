package com.imdatcandan.mobilede

import com.imdatcandan.mobilede.data.CarRepository
import com.imdatcandan.mobilede.domain.CarImage
import com.imdatcandan.mobilede.domain.GetCarImagesUseCaseImpl
import com.imdatcandan.mobilede.domain.NoImagesException
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetCarImagesUseCaseImplTest {

    private lateinit var carRepository: CarRepository
    private lateinit var useCase: GetCarImagesUseCaseImpl

    @Before
    fun setup() {
        carRepository = mockk()
        useCase = GetCarImagesUseCaseImpl(carRepository)
    }

    @Test
    fun `invoke returns Success when repository returns images`() = runTest {
        val images = listOf(
            CarImage( "url1"),
            CarImage("url2")
        )

        coEvery { carRepository.getCarImages("285041801") } returns images

        val result = useCase("285041801")

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        coVerify { carRepository.getCarImages("285041801") }
    }

    @Test
    fun `invoke returns Failure with NoImagesException when repository returns empty list`() = runTest {
        coEvery { carRepository.getCarImages("285041801") } returns emptyList()

        val result = useCase("285041801")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NoImagesException)
        coVerify { carRepository.getCarImages("285041801") }
    }

    @Test
    fun `invoke returns Failure when repository throws exception`() = runTest {
        val exception = RuntimeException("Server error")
        coEvery { carRepository.getCarImages("285041801") } throws exception

        val result = useCase("285041801")

        assertTrue(result.isFailure)
        assertEquals("Server error", result.exceptionOrNull()?.message)
        coVerify { carRepository.getCarImages("285041801") }
    }
}
