package br.com.tramalho.data.provider

import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.data.entity.meal.response.MealCategoryResponse
import br.com.tramalho.data.infraestructure.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class MealProvider(private val service: MealService) {

    fun fetchCategories(): Deferred<Resource<MealCategoryResponse>> {

        return callAsync {
                service.getMealCategory()
        }
    }

    suspend fun fetchMealByCategory(category: String): List<Meal> {
        delay(TimeUnit.SECONDS.toMillis(10))
        return emptyList()
    }
}
