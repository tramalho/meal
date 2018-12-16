package br.com.tramalho.meal.presentation

import android.view.View.VISIBLE
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.data.entity.meal.MealsAndCategories
import br.com.tramalho.data.infraestructure.*
import br.com.tramalho.data.infraestructure.DataMock.Companion.CATEGORY
import br.com.tramalho.domain.business.MealListBusiness
import br.com.tramalho.meal.data.infraestructure.UI
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MealViewModelTest {

    @MockK(relaxUnitFun = true)
    private lateinit var business: MealListBusiness

    @MockK(relaxUnitFun = true)
    private lateinit var observerSuccess: Observer<List<Meal>>

    @MockK(relaxUnitFun = true)
    private lateinit var observerError: Observer<Int>

    @MockK(relaxUnitFun = true)
    private lateinit var application: MealApplication

    private val mAc = DataMock.createMealAndCategory()

    private val meal = DataMock.createMeal()

    private lateinit var viewModel : MealViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MealViewModel(business, application, UI)
        every { application.getString(any()) } returns " "
    }

    @Test
    fun shouldBeRetriveFirtAccessWithSuccees() = runBlocking {
        viewModel.dataReceived.observeForever(observerSuccess)

        coEvery { business.fetchMealsAndCategories() } returns Success(mAc)

        viewModel.start()

        verify(exactly = 1) { observerSuccess.onChanged(mAc.meals) }
    }

    @Test
    fun shouldBeRetriveFirstAccessWithError() = runBlocking {
        viewModel.alternativePageVisibility.observeForever(observerError)

        coEvery { business.fetchMealsAndCategories() } returns Failure(Error(), DataNotAvailable())

        viewModel.start()

        verify(exactly = 1) { observerError.onChanged(VISIBLE) }
    }

    @Test
    fun shouldBeRetriveFetchMealsSuccess() = runBlocking {

        viewModel.dataReceived.observeForever(observerSuccess)

        coEvery { business.fetchMealsAndCategories() } returns Success(mAc)

        coEvery { business.fetchMealsByCategory(CATEGORY)} returns Success(listOf(meal))

        viewModel.start()

        viewModel.fetchMeals()

        verify { observerSuccess.onChanged(listOf(meal)) }

        coVerify(exactly = 1) { business.fetchMealsByCategory(CATEGORY) }
    }

    @Test
    fun shouldNotExecuteWithoutCategories() = runBlocking {

        viewModel.dataReceived.observeForever(observerSuccess)

        coEvery { business.fetchMealsAndCategories() } returns Success(MealsAndCategories(emptyList(), emptyList()))

        coEvery { business.fetchMealsByCategory(CATEGORY)} returns Success(listOf(meal))

        viewModel.start()

        viewModel.fetchMeals()

        verify(exactly = 0) { observerSuccess.onChanged(listOf(meal)) }

        coVerify(exactly = 0) { business.fetchMealsByCategory(CATEGORY) }
    }
}

