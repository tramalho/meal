package br.com.tramalho.meal.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.infraestructure.handle
import br.com.tramalho.domain.business.MealListBusiness
import br.com.tramalho.meal.utilities.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MealViewModel(private val mealListBusiness : MealListBusiness) : ViewModel() {

    val dataReceived: SingleLiveEvent<List<MealCategory>> = SingleLiveEvent()
    val error: SingleLiveEvent<Nothing> = SingleLiveEvent()

    fun start() {

        GlobalScope.launch(Dispatchers.Main) {

            val fetchMealsCategories = mealListBusiness.fetchMealsCategories()

            fetchMealsCategories.handle(
                {
                    dataReceived.value = this.data
                    Log.d("data", "data ${this.data.size}")
                },
                {
                    Log.d("abacate", "abacate")
                    error.value = null
                }
            )
        }
    }
}