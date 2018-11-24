package br.com.tramalho.meal.presentation

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.entity.meal.MealsAndCategories
import br.com.tramalho.data.infraestructure.*
import br.com.tramalho.domain.business.MealListBusiness
import br.com.tramalho.meal.presentation.ListStatus.Companion.HEADER
import br.com.tramalho.meal.presentation.ListStatus.Companion.ITEM
import br.com.tramalho.meal.utilities.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MealViewModel(private val mealListBusiness: MealListBusiness, val coroutineContext: CoroutineDispatcher = UI) :
    ViewModel() {

    val dataReceived: SingleLiveEvent<List<Meal>> = SingleLiveEvent()
    val error: SingleLiveEvent<State> = SingleLiveEvent()

    val listVisibility = MutableLiveData<Int>().apply { value = GONE }
    val loading = MutableLiveData<Int>().apply { value = VISIBLE }

    private var categories: Iterator<MealCategory> = listOf<MealCategory>().iterator()

    fun start() {

        GlobalScope.launch(coroutineContext) {
            val resource = mealListBusiness.fetchMealsAndCategories()
            resource.handle(success(), failure())
        }
    }

    fun fetchMeals() {

        GlobalScope.launch(coroutineContext) {
            if (categories.hasNext()) {
                val mealCategory = categories.next()
                mealListBusiness.fetchMealsByCategory(mealCategory.strCategory)
                    .handle(mealSucess(), failure())
            }
        }
    }

    private fun failure(): Failure.() -> Unit = {
        error.value = this.state
        configVisibility(ViewState.ERROR)
    }

    private fun success(): Success<MealsAndCategories>.() -> Unit = {

        val result = this.data.categories

        when (result.isNotEmpty()) {

            true -> {
                categories = result.iterator()
                categories.next()

                configureType(data.meals)

                dataReceived.value = data.meals
                configVisibility(ViewState.SUCCESS)
            }

            false -> configVisibility(ViewState.NO_DATA)
        }
    }

    private fun configVisibility(viewState: ViewState) {

        val result = when (viewState) {
            ViewState.LOADING -> Status(VISIBLE, GONE)
            ViewState.SUCCESS -> Status(GONE, VISIBLE)
            ViewState.ERROR -> Status(GONE, GONE, VISIBLE)
            ViewState.NO_DATA -> Status(GONE, GONE, GONE, VISIBLE)
        }

        loading.value = result.loading
        listVisibility.value = result.list
    }

    private fun mealSucess(): Success<List<Meal>>.() -> Unit {
        return {
            configureType(this.data)
            dataReceived.value = data
        }
    }

    private fun configureType(data: List<Meal>) {

        data.forEach { it.type = ITEM }

        val first = data.first()
        val last = dataReceived.value?.last()
        last.takeIf { last?.category != first.category }.apply { first.type = HEADER }
    }

    private data class Status(
        val loading: Int,
        val list: Int,
        val errorView: Int = GONE,
        val emptyView: Int = GONE
    )

    private enum class ViewState { SUCCESS, ERROR, LOADING, NO_DATA }
}