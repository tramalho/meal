package br.com.tramalho.meal.presentation

import android.app.Application
import android.view.View.GONE
import androidx.lifecycle.MutableLiveData
import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.entity.meal.MealsAndCategories
import br.com.tramalho.data.infraestructure.Failure
import br.com.tramalho.data.infraestructure.Success
import br.com.tramalho.data.infraestructure.handle
import br.com.tramalho.domain.business.MealListBusiness
import br.com.tramalho.meal.presentation.ListStatus.Companion.HEADER
import br.com.tramalho.meal.presentation.ListStatus.Companion.ITEM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MealViewModel(
    private val mealListBusiness: MealListBusiness,
    application: Application,
    private val coroutineScope: CoroutineScope
) : BaseViewModel(application) {

    val dataReceived: MutableLiveData<ArrayList<Meal>> = MutableLiveData()
    val listVisibility = MutableLiveData<Int>().apply { value = GONE }

    private var categories: Iterator<MealCategory> = listOf<MealCategory>().iterator()

    fun start() {
        //first open screen
        if (dataReceived.value.isNullOrEmpty()) {

            configVisibility(ViewState.LOADING)
            coroutineScope.launch {
                val resource = mealListBusiness.fetchMealsAndCategories()
                resource.handle(success(), failure())
            }
        }
        //back from details
        else {
            dataReceived.value = dataReceived.value
            configVisibility(ViewState.SUCCESS)
        }
    }

    fun fetchMeals() {
        coroutineScope.launch() {
            if (categories.hasNext()) {
                val mealCategory = categories.next()
                mealListBusiness.fetchMealsByCategory(mealCategory.strCategory)
                    .handle(mealSuccess(), failure())
            }
        }
    }

    private fun failure(): Failure.() -> Unit = {
        configVisibility(ViewState.ERROR)
    }

    private fun success(): Success<MealsAndCategories>.() -> Unit = {

        val result = this.data.meals

        if (result.isNotEmpty()) {

            categories = this.data.categories.iterator()
            categories.next()

            configureType(result)

            addValue(result)
            configVisibility(ViewState.SUCCESS)
        } else {
            configVisibility(ViewState.NO_DATA)
        }
    }

    override fun configVisibility(viewState: ViewState) {

        super.configVisibility(viewState)
        val result = setupViewState(viewState)

        listVisibility.value = result.showData
    }

    private fun mealSuccess(): Success<List<Meal>>.() -> Unit {
        return {
            configureType(this.data)
            addValue(this.data)
        }
    }

    private fun addValue(data: List<Meal>) {

        val mergeData = ArrayList<Meal>()

        dataReceived.value?.let {
            mergeData.addAll(dataReceived.value as ArrayList)
        }

        mergeData.addAll(data)

        dataReceived.value = mergeData
    }

    private fun configureType(data: List<Meal>) {

        if (data.isNotEmpty()) {

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