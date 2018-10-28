package br.com.tramalho.meal.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.infraestructure.handle
import br.com.tramalho.domain.business.MealListBusiness
import br.com.tramalho.meal.utilities.SingleLiveEvent
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

class MealViewModel(private val mealListBusiness : MealListBusiness) : ViewModel() {

    val dataReceived: SingleLiveEvent<List<MealCategory>> = SingleLiveEvent()
    val error: SingleLiveEvent<List<MealCategory>> = SingleLiveEvent()

    fun start(ctx : Context) {

        GlobalScope.launch(Dispatchers.Main) {

            val fetchMealsCategories = mealListBusiness.fetchMealsCategories()

            fetchMealsCategories.handle(
                {
                    dataReceived.value = this.data
                    Log.d("data", "data ${this.data.size}")
                },
                {
                    Log.d("abacate", "abacate")
                    error.value = listOf()
                }
            )
        }
    }

}