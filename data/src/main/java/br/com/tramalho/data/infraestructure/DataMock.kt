package br.com.tramalho.data.infraestructure

import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.data.entity.meal.MealCategory
import br.com.tramalho.data.entity.meal.MealsAndCategories

class DataMock {


    companion object {
        fun createCategory(): MealCategory = MealCategory(
            CATEGORY,
            CATEGORY,
            CATEGORY
        )
        fun createMeal() = Meal(
            MEAL,
            MEAL,
            MEAL
        )
        fun createMealAndCategory() = MealsAndCategories(
            listOf(createMeal()),
            listOf(createCategory(), createCategory()))

        const val CATEGORY: String = "CATEGORY"
        const val MEAL: String = "MEAL"
    }
}