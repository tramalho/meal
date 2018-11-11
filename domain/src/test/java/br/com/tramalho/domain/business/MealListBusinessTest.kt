package br.com.tramalho.domain.business

import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.entity.meal.response.MealCategoryResponse
import br.com.tramalho.data.infraestructure.Failure
import br.com.tramalho.data.infraestructure.Success
import br.com.tramalho.data.infraestructure.handle
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
    private lateinit var mealProvider: MealProvider

    private val mealCategory: MealCategory = MealCategory(CATEGORY)

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        business = MealListBusiness(localProvider, mealProvider)
    }

    @Test
    fun shouldBeRetrieveFromLocal() = runBlocking {

        every { localProvider.fetchCategories() } returns listOf(mealCategory)

        val fetchMealsCategories = business.fetchMealsCategories()

        fetchMealsCategories.handle({ assertEquals(mealCategory, data[0]) })
    }

    @Test
    fun shouldBeRetrieveFromRemote() = runBlocking {

        every { localProvider.fetchCategories() } returns listOf()

        coEvery { mealProvider.fetchCategories().await() } returns Success(MealCategoryResponse(listOf(mealCategory)))

        val fetchMealsCategories = business.fetchMealsCategories()

        coVerify { localProvider.saveCategories(any()) }

        fetchMealsCategories.handle({ assertEquals(mealCategory, data[0]) })
    }


    @Test
    fun shouldBeError() = runBlocking {

        every { localProvider.fetchCategories() } returns listOf()

        coEvery { mealProvider.fetchCategories().await() } returns Failure(Error(CATEGORY))

        val fetchMealsCategories = business.fetchMealsCategories()

        verify(exactly = 0) { localProvider.saveCategories(any()) }

        fetchMealsCategories.handle({ fail("not execute") }, { assertEquals(CATEGORY, data.message) })
    }

    companion object {
        const val CATEGORY: String = "CATEGORY"
    }
}