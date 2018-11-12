package br.com.tramalho.domain.business

import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.entity.meal.response.MealsAndCategories
import br.com.tramalho.data.infraestructure.Failure
import br.com.tramalho.data.infraestructure.Resource
import br.com.tramalho.data.infraestructure.Success
import br.com.tramalho.data.provider.LocalProvider
import br.com.tramalho.data.provider.MealProvider

class MealListBusiness(private val localProvider: LocalProvider, private val mealProvider: MealProvider) {

    suspend fun fetchCategories(): Resource<List<MealCategory>> {

        var categories = localProvider.fetchCategories()
        var result: Resource<List<MealCategory>> = Success(categories)

        if (categories.isEmpty()) {

            val response = mealProvider.fetchCategories().await()

            when (response) {
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


    suspend fun fetchMealsByCategory(category: String): Resource<List<Meal>> {

        val response = mealProvider.fetchMealByCategory(category).await()

        return when (response) {
            is Success -> Success(response.data.meals)
            is Failure -> Failure(response.data)
        }
    }

    suspend fun fetchMealsAndCategories(): Resource<MealsAndCategories> {

        val catResource = fetchCategories()

        if (catResource is Failure) {
            return Failure(catResource.data)
        }

        val categoryList = (catResource as Success).data

        val response = fetchMealsByCategory(categoryList.first().strCategory)

        return when (response) {
            is Success -> Success(MealsAndCategories(response.data, categoryList))
            is Failure -> Failure(response.data)
        }
    }

}