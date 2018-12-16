package br.com.tramalho.data.provider

import br.com.tramalho.data.infraestructure.MealService
import br.com.tramalho.data.infraestructure.callAsync

class MealDetailProvider(private val service: MealService) {

    fun fetchDetailById(id: String) =
        callAsync { service.fetchDetailById(id) }
}