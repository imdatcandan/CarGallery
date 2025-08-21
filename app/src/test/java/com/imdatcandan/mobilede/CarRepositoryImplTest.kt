import com.imdatcandan.mobilede.data.ApiException
import com.imdatcandan.mobilede.data.CarApiService
import com.imdatcandan.mobilede.data.CarRepositoryImpl
import com.imdatcandan.mobilede.data.NetworkException
import com.imdatcandan.mobilede.data.model.CarResponseDto
import com.imdatcandan.mobilede.data.model.ImageDto
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CarRepositoryImplTest {

    private lateinit var api: CarApiService
    private lateinit var repository: CarRepositoryImpl

    @Before
    fun setup() {
        api = mockk()
        repository = CarRepositoryImpl(api)
    }

    @Test
    fun `getCarImages returns mapped images when API succeeds`() = runTest {
        val apiResponse = CarResponseDto(
                images = listOf(
                    ImageDto("url1"),
                    ImageDto("url2"),
                )
        )

        coEvery { api.getCarDetails("285041801") } returns apiResponse

        val result = repository.getCarImages("285041801")

        assertEquals(2, result.size)
        assertEquals("url1", result[0].baseUri)
        coVerify { api.getCarDetails("285041801") }
    }

    @Test
    fun `getCarImages throws NetworkException when IOException occurs`() = runTest {
        coEvery { api.getCarDetails("285041801") } throws IOException("No connection")

        try {
            repository.getCarImages("285041801")
            assert(false) // Should not reach here
        } catch (e: NetworkException) {
            assertTrue(e.message!!.contains("Network error"))
            assertTrue(e.cause is IOException)
        }

        coVerify { api.getCarDetails("285041801") }
    }

    @Test
    fun `getCarImages throws ApiException when HttpException occurs`() = runTest {
        val httpException = HttpException(Response.error<Any>(500, mockk(relaxed = true)))
        coEvery { api.getCarDetails("285041801") } throws httpException

        try {
            repository.getCarImages("285041801")
            assert(false) // Should not reach here
        } catch (e: ApiException) {
            assertTrue(e.message!!.contains("API error"))
            assertTrue(e.cause is HttpException)
        }

        coVerify { api.getCarDetails("285041801") }
    }
}
