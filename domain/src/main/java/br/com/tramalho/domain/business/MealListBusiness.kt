package br.com.tramalho.domain.business

import android.util.Log
import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.entity.meal.response.MealCategoryResponse
import br.com.tramalho.data.infraestructure.Failure
import br.com.tramalho.data.infraestructure.Resource
import br.com.tramalho.data.infraestructure.Success
import br.com.tramalho.data.infraestructure.handle
import br.com.tramalho.data.provider.LocalProvider
import br.com.tramalho.data.provider.MealProvider

class MealListBusiness(private val localProvider: LocalProvider, private val mealProvider: MealProvider) {

    suspend fun fetchMealsCategories(): Resource<List<MealCategory>> {

        var categories = localProvider.fetchCategories()
        var result : Resource<List<MealCategory>> = Success(categories)

        if(categories.isEmpty()) {

            val response = mealProvider.fetchCategories().await()

            when(response) {
                is Success -> {
                    categories = response.data.categories
                    localProvider.saveCategories(categories)
                    result = Success(categories)
                }
                is Failure ->
                    result = Failure(response.data)
            }
        }

        return result
    }

    suspend fun fetchMealsByCategories(category: String) = mealProvider.fetchMealByCategory(category)
}