package br.com.tramalho.data.infraestructure

import retrofit2.Retrofit

class ServiceFactory(private val retrofit: Retrofit){

    fun makeMealService(): MealService = retrofit.create(MealService::class.java)
}