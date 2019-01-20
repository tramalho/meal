package br.com.tramalho.domain.business

import br.com.tramalho.data.entity.meal.MealDetail
import br.com.tramalho.data.entity.meal.response.MealDetailResponse
import br.com.tramalho.data.infraestructure.Failure
import br.com.tramalho.data.infraestructure.Resource
import br.com.tramalho.data.infraestructure.Success
import br.com.tramalho.data.provider.MealDetailProvider

class MealDetailBusiness(private val mealDetailProvider: MealDetailProvider) {

    suspend fun fetchDetails(id: String): Resource<MealDetail> {

        var response: Resource<MealDetailResponse> = Failure(Error("Illegal argument id"))

        if (!id.isBlank()) {
            response = mealDetailProvider.fetchDetailById(id).await()
        }

        return when (response) {
            is Success -> Success(response.data.meals[0])
            is Failure -> Failure(response.data, response.networkState)
        }
    }
}