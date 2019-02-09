package br.com.tramalho.meal.presentation

import android.view.View.VISIBLE
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.tramalho.data.entity.meal.MealDetail
import br.com.tramalho.data.infraestructure.DataMock
import br.com.tramalho.data.infraestructure.DataNotAvailable
import br.com.tramalho.data.infraestructure.Failure
import br.com.tramalho.data.infraestructure.Success
import br.com.tramalho.domain.business.MealDetailBusiness
import br.com.tramalho.meal.data.infraestructure.UI
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class MealDetailViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel : MealDetailViewModel

    @MockK
    private lateinit var business: MealDetailBusiness

    @MockK
    private lateinit var observerSuccess: Observer<MealDetail>

    @MockK
    private lateinit var observerError: Observer<Int>

    @MockK
    private lateinit var application: MealApplication

    private val mealDetail = DataMock.createMealDetail()


    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MealDetailViewModel(business, application, UI)
        every { application.getString(any()) } returns " "
    }

    @Test
    fun shouldReturnSuccess(){

        viewModel.mealDetails.observeForever(observerSuccess)

        coEvery { business.fetchDetails(anyString()) } returns Success(mealDetail)

        viewModel.start(anyString())

        verify(exactly = 1) { observerSuccess.onChanged(mealDetail) }
    }

    @Test
    fun shouldReturnError(){

        viewModel.alternativePageVisibility.observeForever(observerError)

        coEvery { business.fetchDetails(anyString()) } returns Failure(Error(), DataNotAvailable())

        viewModel.start(anyString())

        viewModel.tryAgain()

        verify(exactly = 2) { observerError.onChanged(VISIBLE) }
    }
}