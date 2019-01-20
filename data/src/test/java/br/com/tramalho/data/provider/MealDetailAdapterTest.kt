package br.com.tramalho.data.provider

import br.com.tramalho.data.entity.meal.response.MealDetailResponse
import br.com.tramalho.data.infraestructure.MealDetailJsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class MealDetailAdapterTest {

    private val fullJson = "{\n" +
            "  \"meals\": [\n" +
            "    {\n" +
            "      \"idMeal\": \"52776\",\n" +
            "      \"strMeal\": \"Chocolate Gateau\",\n" +
            "      \"strCategory\": \"Desert\",\n" +
            "      \"strArea\": \"French\",\n" +
            "      \"strInstructions\": \"abc\\ncde\",\n" +
            "      \"strMealThumb\": \"\",\n" +
            "      \"strTags\": \" \",\n" +
            "      \"strYoutube\": \"strYoutube\",\n" +
            "      \"strIngredient1\": \"cajun\",\n" +
            "      \"strIngredient20\": \"onion\",\n" +
            "      \"strMeasure1\": \"strMeasure1\",\n" +
            "      \"strMeasure2\": \"strMeasure2\",\n" +
            "      \"strSource\": \"https2\",\n" +
            "      \"dateModified\": null\n" +
            "    }\n" +
            "  ]\n" +
            "}"

    private val emptyJson = "{\"meals\": []}"

    private lateinit var moshi: Moshi

    @Before
    fun setUp(){
        moshi = Moshi.Builder()
            .add(MealDetailJsonAdapter())
            .build()
    }

    @Test
    fun shouldBeParse() {
        val adapter =
            moshi.adapter(MealDetailResponse::class.java)
        val mealDetailResponse = adapter.fromJson(fullJson)

        val mealDetail = mealDetailResponse?.meals?.get(0)

        assertNotNull(mealDetail)

        assertEquals("https2", mealDetail?.strSource)
        //explicit null value
        assertEquals(" ", mealDetail?.dateModified)

        //not send in json
        assertEquals(" ", mealDetail?.strTags)

        val ingredients = mealDetail?.ingredients

        assertEquals("cajun", ingredients?.get(0))
        assertEquals("onion", ingredients?.get(ingredients.size-1))

        val measures = mealDetail?.measures

        assertEquals("strMeasure1", measures?.get(0))
        assertEquals("strMeasure2", measures?.get(measures.size-1))

        assertEquals("cde", mealDetail?.strInstructions!![1])
    }
    @Test
    fun shouldBeReturnEmptyMealDetail() {

        val adapter =
            moshi.adapter(MealDetailResponse::class.java)

        val mealDetailResponse = adapter.fromJson(emptyJson)

        assertTrue(mealDetailResponse!!.meals.isEmpty())
    }
}