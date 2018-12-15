package br.com.tramalho.data.entity.meal

data class MealDetail(
    val idMeal: String = "",
    val strMeal: String = "",
    val strCategory: String = "",
    val strArea: String = "",
    val strInstructions: String = "",
    val strTags: String = "",
    val strYoutube: String = "",
    val strSource: String = "",
    val dateModified: String = "",
    val ingredients: ArrayList<String> = arrayListOf(),
    val measures: ArrayList<String> = arrayListOf()
)