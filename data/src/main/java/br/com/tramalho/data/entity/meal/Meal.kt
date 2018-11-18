package br.com.tramalho.data.entity.meal

data class Meal(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String,
    var category: String = "",
    var favorite: Boolean = false
)