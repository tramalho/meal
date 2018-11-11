package br.com.tramalho.data.provider

import android.content.Context
import br.com.tramalho.data.entity.meal.MealCategory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType


class LocalProvider(private val context: Context) {

    fun fetchCategories(): List<MealCategory> {

        val fromJson : ArrayList<MealCategory> = ArrayList()

        val strPref = getJsonFromPref(MEAL_CATEGORY)

        if(strPref.isNotBlank()) {

            val type = getListAdapterType()

            with(getAdapter<List<MealCategory>>(type)){

                fromJson(strPref)?.apply {
                    fromJson.addAll(this)
                }
            }
        }

        return fromJson
    }


    fun saveCategories(categories: List<MealCategory>) {

        val type = getListAdapterType()

        val adapter= getAdapter<List<MealCategory>>(type)

        val serializedJson = adapter.toJson(categories)

        save(MEAL_CATEGORY, serializedJson)
    }

    private fun save(key: String, value: String) {

        takeIf { value.isNotBlank() }
            .apply {
                getPref()
                    .edit()
                    .putString(key, value)
                    .apply()
            }
    }

    private fun <T> getAdapter(type: ParameterizedType): JsonAdapter<T> {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<T> = moshi.adapter(type)
        return adapter
    }

    private fun getListAdapterType() = Types.newParameterizedType(List::class.java, Object::class.java)

    private fun getJsonFromPref(key: String): String = with(getPref()) { getString(key, "")!! }

    private fun getPref() = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

    private companion object {
        const val PREF_KEY = "PREF_KEY"
        const val MEAL_CATEGORY = "MEAL_CATEGORY"
    }
}