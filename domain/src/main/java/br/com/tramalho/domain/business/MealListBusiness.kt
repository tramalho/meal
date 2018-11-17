package br.com.tramalho.domain.business

import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.entity.meal.MealsAndCategories
import br.com.tramalho.data.entity.meal.response.MealCategoryResponse
import br.com.tramalho.data.infraestructure.Failure
import br.com.tramalho.data.infraestructure.Resource
import br.com.tramalho.data.infraestructure.Success
import br.com.tramalho.data.provider.LocalProvider
import br.com.tramalho.data.provider.MealProvider

class MealListBusiness(private val localProvider: LocalProvider, private val mealProvider: MealProvider) {

    suspend fun fetchCategories(): Resource<List<MealCategory>> {

        val categories = localProvider.fetchCategories()

        var result: Resource<List<MealCategory>> = Success(categories)

        if (categories.isEmpty()) {

            val response = mealProvider.fetchCategories().await()

            when (response) {
                is Success -> {
                    result = configCategoryResponse(response)
                }
                is Failure ->
                    result = Failure(response.data)
            }
        }

        return result
    }

    private fun configCategoryResponse(response: Success<MealCategoryResponse>): Resource<List<MealCategory>> {

        val categories = response.data.categories

        return when (categories.isNullOrEmpty()) {
            true -> Failure(Error())
            false -> {
                localProvider.saveCategories(response.data.categories)
                Success(response.data.categories)
            }
        }
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
            return catResource
        }

        val categoryList = (catResource as Success).data

        val response = fetchMealsByCategory(categoryList.first().strCategory)

        return when (response) {
            is Success -> Success(MealsAndCategories(response.data, categoryList))
            is Failure -> Failure(response.data)
        }
    }

}