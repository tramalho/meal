package br.com.tramalho.domain.business

import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.entity.meal.response.MealCategoryResponse
import br.com.tramalho.data.entity.meal.response.MealResponse
import br.com.tramalho.data.infraestructure.*
import br.com.tramalho.data.infraestructure.DataMock.Companion.CATEGORY
import br.com.tramalho.data.infraestructure.DataMock.Companion.MEAL
import br.com.tramalho.data.provider.LocalProvider
import br.com.tramalho.data.provider.MealProvider
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test


class MealListBusinessTest {

    private lateinit var business: MealListBusiness

    @MockK(relaxUnitFun = true)
    private lateinit var localProvider: LocalProvider

    @MockK(relaxUnitFun = true)
    private lateinit var remoteProvider: MealProvider

    private val mealCategory: MealCategory = DataMock.createCategory()

    private val meal = DataMock.createMeal()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        business = MealListBusiness(localProvider, remoteProvider)
    }

    @Test
    fun shouldBeRetrieveFromLocal() = runBlocking {

        every { localProvider.fetchCategories() } returns listOf(mealCategory)

        val fetchMealsCategories = business.fetchCategories()

        fetchMealsCategories.handle({ assertEquals(mealCategory, data[0]) })
    }

    @Test
    fun shouldBeRetrieveFromRemote() = runBlocking {

        every { localProvider.fetchCategories() } returns listOf()

        coEvery { remoteProvider.fetchCategories().await() } returns Success(
            MealCategoryResponse(listOf(mealCategory))
        )

        val fetchMealsCategories = business.fetchCategories()

        coVerify { localProvider.saveCategories(any()) }

        fetchMealsCategories.handle({ assertEquals(mealCategory, data[0]) })
    }


    @Test
    fun shouldBeErrorFromRemote() = runBlocking {

        every { localProvider.fetchCategories() } returns listOf()

        coEvery { remoteProvider.fetchCategories().await() } returns Failure(Error(CATEGORY))

        val fetchMealsCategories = business.fetchCategories()

        verify(exactly = 0) { localProvider.saveCategories(any()) }

        fetchMealsCategories.handle({ fail() }, { assertEquals(CATEGORY, data.message) })
    }

    @Test
    fun shouldBeErrorDataNotAvailable() = runBlocking {

        every { localProvider.fetchCategories() } returns listOf()

        coEvery { remoteProvider.fetchCategories().await() } returns Success(
            MealCategoryResponse(listOf())
        )

        val fetchMealsCategories = business.fetchCategories()

        verify(exactly = 0) { localProvider.saveCategories(any()) }

        fetchMealsCategories.handle({ fail() }, { assertEquals(DataNotAvailable().javaClass, this.networkState.javaClass) })
    }

    @Test
    fun shouldBeRetrieveCatAndMeal() = runBlocking {

        every { localProvider.fetchCategories() } returns listOf(mealCategory)

        coEvery { remoteProvider.fetchMealByCategory(any()).await() } returns Success(
            MealResponse(listOf(meal))
        )

        val fetchMealsCategories = business.fetchMealsAndCategories()

        fetchMealsCategories.handle({
            assertEquals(meal, data.meals[0])
            assertEquals(mealCategory, data.categories[0])
        })
    }

    @Test
    fun shouldBeRetrieveErrorCatAndMeal() = runBlocking {

        every { localProvider.fetchCategories() } returns listOf()

        coEvery { remoteProvider.fetchCategories().await() } returns Failure(
            Error(
                MEAL
            )
        )

        val fetchMealsCategories = business.fetchMealsAndCategories()

        fetchMealsCategories.handle({ fail() }, { assertEquals(MEAL, data.message) })

        coVerify(exactly = 0) { remoteProvider.fetchMealByCategory(any()).await() }
    }

    @Test
    fun shouldBeRetrieveErrorWhenFetchMealByCategory() = runBlocking {

        every { localProvider.fetchCategories() } returns listOf(mealCategory)

        coEvery { remoteProvider.fetchMealByCategory(any()).await() } returns Failure(
            Error(MEAL)
        )

        val fetchMealsCategories = business.fetchMealsAndCategories()

        fetchMealsCategories.handle({ fail() }, { assertEquals(DataNotAvailable().javaClass, this.networkState.javaClass) })

        coVerify { remoteProvider.fetchMealByCategory(any()).await() }
    }
}