package br.com.tramalho.data.provider

import br.com.tramalho.data.infraestructure.MealService
import br.com.tramalho.data.infraestructure.callAsync

class MealProvider(private val service: MealService) {

    fun fetchCategories() =
        callAsync {
            service.getMealCategory()
        }

    fun fetchMealByCategory(category: String) =
        callAsync { service.getMealsByCategory(category) }
}
