package br.com.tramalho.data.entity.meal.response

import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.data.entity.meal.MealCategory

data class MealCategoryResponse(val categories : List<MealCategory>)

data class MealResponse(val meals : List<Meal>)
