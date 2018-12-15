package br.com.tramalho.data.entity.meal.response

import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.entity.meal.MealDetail

data class MealCategoryResponse(val categories : List<MealCategory>)

data class MealResponse(val meals : List<Meal>)

data class MealDetailResponse(val meals: List<MealDetail>)