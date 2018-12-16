package br.com.tramalho.meal.presentation

import android.app.Application
import android.view.View.GONE
import androidx.lifecycle.MutableLiveData
import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.entity.meal.MealsAndCategories
import br.com.tramalho.data.infraestructure.Failure
import br.com.tramalho.data.infraestructure.Success
import br.com.tramalho.data.infraestructure.UI
import br.com.tramalho.data.infraestructure.handle
import br.com.tramalho.domain.business.MealListBusiness
import br.com.tramalho.meal.presentation.ListStatus.Companion.HEADER
import br.com.tramalho.meal.presentation.ListStatus.Companion.ITEM
import br.com.tramalho.meal.utilities.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MealViewModel(private val mealListBusiness: MealListBusiness, application: Application, protected val coroutineScope: CoroutineScope = UI) : BaseViewModel(application) {

    val dataReceived: SingleLiveEvent<List<Meal>> = SingleLiveEvent()
    val listVisibility = MutableLiveData<Int>().apply { value = GONE }

    private var categories: Iterator<MealCategory> = listOf<MealCategory>().iterator()

    fun start() {
        configVisibility(ViewState.LOADING)
        coroutineScope.launch {
            val resource = mealListBusiness.fetchMealsAndCategories()
            resource.handle(success(), failure())
        }
    }

    fun fetchMeals() {
        coroutineScope.launch() {
            if (categories.hasNext()) {
                val mealCategory = categories.next()
                mealListBusiness.fetchMealsByCategory(mealCategory.strCategory)
                    .handle(mealSucess(), failure())
            }
        }
    }

    private fun failure(): Failure.() -> Unit = {
        configVisibility(ViewState.ERROR)
    }

    private fun success(): Success<MealsAndCategories>.() -> Unit = {

        val result = this.data.meals

        when (result.isNotEmpty()) {

            true -> {
                categories = this.data.categories.iterator()
                categories.next()

                configureType(data.meals)

                dataReceived.value = data.meals
                configVisibility(ViewState.SUCCESS)
            }

            false -> configVisibility(ViewState.NO_DATA)
        }
    }

    override fun configVisibility(viewState: ViewState) {

        super.configVisibility(viewState)
        val result = setupViewState(viewState)

        listVisibility.value = result.showData
    }

    private fun mealSucess(): Success<List<Meal>>.() -> Unit {
        return {
            configureType(this.data)
            dataReceived.value = data
        }
    }

    private fun configureType(data: List<Meal>) {

        if(data.isNotEmpty()) {

            data.forEach { it.type = ITEM }

            val first = data.first()
            val last = dataReceived.value?.last()
            last.takeIf { last?.category != first.category }.apply { first.type = HEADER }
        }
    }

    override fun tryAgain() {
        start()
    }
}