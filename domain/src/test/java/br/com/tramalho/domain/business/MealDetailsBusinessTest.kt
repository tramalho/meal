package br.com.tramalho.domain.business

import br.com.tramalho.data.entity.meal.response.MealDetailResponse
import br.com.tramalho.data.infraestructure.*
import br.com.tramalho.data.infraestructure.DataMock.Companion.ID_ERROR
import br.com.tramalho.data.infraestructure.DataMock.Companion.ID_MEAL
import br.com.tramalho.data.provider.MealDetailProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test


class MealDetailsBusinessTest {

    private lateinit var business: MealDetailBusiness

    @MockK(relaxUnitFun = true)
    private lateinit var remoteProvider: MealDetailProvider

    private val successResult = DataMock.createMealDetail()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        business = MealDetailBusiness(remoteProvider)
    }

    @Test
    fun shouldReturnSuccess() = runBlocking {

        coEvery { remoteProvider.fetchDetailById("123").await() } returns Success(
            MealDetailResponse(listOf(successResult))
        )

        val remoteResult = business.fetchDetails("123")

        remoteResult.handle({ assertEquals(successResult, data) }, { fail() })

    }

    @Test
    fun shouldReturnFailure() = runBlocking {
        coEvery { remoteProvider.fetchDetailById("123").await() } returns Failure(Error(ID_MEAL), UnexpectedError())

        val remoteResult = business.fetchDetails("123")

        remoteResult.handle({ fail() },
            {
                assertEquals(this.networkState.javaClass, UnexpectedError().javaClass)
                assertEquals(this.data.message, ID_MEAL)
            }
        )

    }

    @Test
    fun shouldReturnFailureEmptyID() = runBlocking {

        val remoteResult = business.fetchDetails("")

        remoteResult.handle({ fail() },
            {
                assertEquals(this.networkState.javaClass, DataNotAvailable().javaClass)
                assertEquals(this.data.message, ID_ERROR)
            }
        )

    }
}