package br.com.tramalho.data.infraestructure

import br.com.tramalho.data.entity.meal.MealDetail
import com.squareup.moshi.*

class MealDetailAdapter() : JsonAdapter<MealDetail>() {

    companion object {
        val UNIQUE_FIELDS = JsonReader.Options.of(
            "idMeal", "strMeal", "strCategory", "strArea",
            "strInstructions", "strTags", "strYoutube", "strSource", "dateModified"
        )

        val INGREDIENTS = JsonReader.Options.of(*Array(20) { "strIngredient${1 + it}" })
        val MEASURES = JsonReader.Options.of(*Array(20) { "strMeasure${1 + it}" })
        val INVALID_IDX = -1
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: MealDetail?) {
        //nothing
    }

    @FromJson
    override fun fromJson(reader: JsonReader): MealDetail {

        //fill array with empty strings always unique fields should be value
        val uniqueFieldsParsed = Array(9) { " " }

        val ingredients = ArrayList<String>()

        val measures = ArrayList<String>()

        while (reader.hasNext()) {

            reader.beginObject()

            while (reader.hasNext()) {

                val uniqueIdx = reader.selectName(UNIQUE_FIELDS)

                when {
                    uniqueIdx > INVALID_IDX -> {
                        uniqueFieldsParsed[uniqueIdx] = safeStringReturn(reader)
                    }
                    reader.selectName(INGREDIENTS) > INVALID_IDX -> {
                        ingredients.add(safeStringReturn(reader))
                    }
                    reader.selectName(MEASURES) > INVALID_IDX -> {
                        measures.add(safeStringReturn(reader))
                    }
                    else -> {
                        reader.skipName()
                        reader.skipValue()
                    }

                }
            }

            reader.endObject()
        }

        return createModel(uniqueFieldsParsed, ingredients, measures)
    }


    private fun safeStringReturn(reader: JsonReader): String {

        return when {
            reader.peek() != JsonReader.Token.NULL -> reader.nextString()
            else -> {
                reader.nextNull<Unit>()
                " "
            }
        }
    }

    private fun createModel(
        uniqueFieldsParsed: Array<String>,
        ingredients: ArrayList<String>,
        measures: ArrayList<String>
    ): MealDetail {

        val iterator = uniqueFieldsParsed.iterator()

        return MealDetail(
            iterator.next(), iterator.next(), iterator.next(),
            iterator.next(), iterator.next(), iterator.next(),
            iterator.next(), iterator.next(), iterator.next(),
            ingredients,
            measures
        )
    }
}
