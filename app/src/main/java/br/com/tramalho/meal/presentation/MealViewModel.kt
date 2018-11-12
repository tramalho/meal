package br.com.tramalho.meal.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.entity.meal.response.MealsAndCategories
import br.com.tramalho.data.infraestructure.Failure
import br.com.tramalho.data.infraestructure.Success
import br.com.tramalho.data.infraestructure.handle
import br.com.tramalho.domain.business.MealListBusiness
import br.com.tramalho.meal.utilities.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MealViewModel(private val mealListBusiness: MealListBusiness) : ViewModel() {

    val dataReceived: SingleLiveEvent<List<MealCategory>> = SingleLiveEvent()
    val error: SingleLiveEvent<Nothing> = SingleLiveEvent()

    private lateinit var categories: Iterator<MealCategory>

    fun start() {
        GlobalScope.launch(Dispatchers.Main) {
            mealListBusiness.fetchMealsAndCategories()
                .handle(success(), failure())
        }
    }

    private fun failure(): Failure.() -> Unit = {
        error.value = null
        Log.d("abacate", "abacate")
    }


    private fun success(): Success<MealsAndCategories>.() -> Unit = {
        categories = this.data.categories.iterator()
        categories.takeIf { categories.hasNext() }.apply { categories.next() }
        dataReceived.value = this.data.categories

        Log.d("data", "data ${this.data.categories.size}")
    }


    private suspend fun fetchMeals() {

        if (categories.hasNext()) {
            val mealCategory = categories.next()
            mealListBusiness.fetchMealsByCategory(mealCategory.strCategory)
        }
    }
}