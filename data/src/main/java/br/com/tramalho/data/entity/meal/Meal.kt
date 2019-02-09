package br.com.tramalho.data.entity.meal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Meal(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String,
    var category: String = "",
    var favorite: Boolean = false,
    var type: Int = 1
) : Parcelable


