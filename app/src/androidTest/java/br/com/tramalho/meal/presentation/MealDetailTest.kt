package br.com.tramalho.meal.presentation

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.tramalho.meal.presentation.setup.BaseInstrumentedTest
import br.com.tramalho.meal.presentation.setup.Constants.Companion.EMPTY_MEAL_BY_CATEGORY
import br.com.tramalho.meal.presentation.setup.Constants.Companion.SUCCESS_CATEGORY
import br.com.tramalho.meal.presentation.setup.Constants.Companion.SUCCESS_MEAL_BY_CATEGORY
import br.com.tramalho.meal.presentation.setup.Constants.Companion.SUCCESS_MEAL_DETAIL
import br.com.tramalho.meal.presentation.setup.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MealDetailTest : BaseInstrumentedTest() {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    private val mealListRobot = MealListRobot()
    private val mealDetailRobot = MealDetailRobot()

    @Test
    fun shouldShowDetailStep() {

        setupMockWebServer(SUCCESS_CATEGORY)

        setupMockWebServer(SUCCESS_MEAL_BY_CATEGORY)

        setupMockWebServer(SUCCESS_MEAL_DETAIL)

        activityRule.launchActivity(Intent())

        verify {
            mealListRobot
                .waitSuccessView()
                .clickFirstMeal()
            mealDetailRobot
                .clickInstructionsTab()
                .checkFirstStep()
        }
    }

    @Test
    fun shouldShowEmptyView() {

        setupMockWebServer(SUCCESS_CATEGORY)

        setupMockWebServer(EMPTY_MEAL_BY_CATEGORY)

        activityRule.launchActivity(Intent())

        verify {
            mealListRobot
                .waitAlternativeView()
                .withEmptyImage()
                .withEmptyText()
                .clickInTryAgain()
        }
    }

    @Test
    fun shouldShowErrorView() {

        setupMockWebServer(SUCCESS_CATEGORY)

        setupMockWebServer(EMPTY_MEAL_BY_CATEGORY, statusCode = 404)

        activityRule.launchActivity(Intent())

        verify {
            mealListRobot
                .waitAlternativeView()
                .withErrorImage()
                .withErrorText()
                .clickInTryAgain()
        }
    }
}