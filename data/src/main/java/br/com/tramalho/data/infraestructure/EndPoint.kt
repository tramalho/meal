package br.com.tramalho.data.infraestructure

import br.com.tramalho.data.entity.meal.response.MealCategoryResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface MealService {
    @GET("categories.php")
    fun getMealCategory(): Deferred<Response<MealCategoryResponse>>
}