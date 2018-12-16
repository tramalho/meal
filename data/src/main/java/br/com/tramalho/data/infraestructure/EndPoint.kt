package br.com.tramalho.data.infraestructure

import br.com.tramalho.data.entity.meal.response.MealCategoryResponse
import br.com.tramalho.data.entity.meal.response.MealDetailResponse
import br.com.tramalho.data.entity.meal.response.MealResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {
    @GET("categories.php")
    fun getMealCategory(): Deferred<Response<MealCategoryResponse>>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category : String): Deferred<Response<MealResponse>>

    @GET("lookup.php")
    fun fetchDetailById(@Query("i") id : String): Deferred<Response<MealDetailResponse>>
}