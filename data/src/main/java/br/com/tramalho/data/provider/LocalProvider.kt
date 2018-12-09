package br.com.tramalho.data.provider

import android.content.SharedPreferences
import br.com.tramalho.data.entity.meal.MealCategory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType


open class LocalProvider(private val sharedPreference: SharedPreferences, private val moshi: Moshi) {

    open fun fetchCategories(): List<MealCategory> {

        val fromJson: ArrayList<MealCategory> = ArrayList()

        val strPref = getJsonFromPref(MEAL_CATEGORY)

        if (strPref.isNotBlank()) {

            val type = getListAdapterType()

            val adapter = getAdapter<List<MealCategory>>(type)

            adapter.fromJson(strPref)?.let { fromJson.addAll(it) }
        }

        return fromJson
    }


    open fun saveCategories(categories: List<MealCategory>) {

        val type = getListAdapterType()

        val adapter = getAdapter<List<MealCategory>>(type)

        val serializedJson = adapter.toJson(categories)

        save(MEAL_CATEGORY, serializedJson)
    }

    private fun save(key: String, value: String) {

        sharedPreference
            .edit()
            .putString(key, value)
            .apply()
    }

    private fun <T> getAdapter(type: ParameterizedType): JsonAdapter<T> = moshi.adapter(type)

    private fun getListAdapterType() =
        Types.newParameterizedType(List::class.java, MealCategory::class.java)

    private fun getJsonFromPref(key: String): String = with(sharedPreference) { getString(key, "")!! }

    private companion object {
        const val MEAL_CATEGORY = "MEAL_CATEGORY"
    }
}