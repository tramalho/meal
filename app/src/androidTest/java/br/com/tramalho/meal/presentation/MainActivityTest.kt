package br.com.tramalho.meal.presentation

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.tramalho.meal.presentation.setup.BaseInstrumentedTest
import br.com.tramalho.meal.presentation.setup.Constants.Companion.EMPTY_MEAL_BY_CATEGORY
import br.com.tramalho.meal.presentation.setup.Constants.Companion.SUCCESS_CATEGORY
import br.com.tramalho.meal.presentation.setup.Constants.Companion.SUCCESS_MEAL_BY_CATEGORY
import br.com.tramalho.meal.presentation.setup.verify
import io.mockk.every
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseInstrumentedTest() {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    private val mainActivityRobot = MainActivityRobot()

    @Test
    fun shouldShowListOfItens() {

        every { localProvider.fetchCategories() } returns listOf()

        setupMockWebServer(SUCCESS_CATEGORY)

        setupMockWebServer(SUCCESS_MEAL_BY_CATEGORY)

        activityRule.launchActivity(Intent())

        verify {
            mainActivityRobot
                .waitSuccessView()
                .matchText("BeefandMustardPie")
        }
    }

    @Test
    fun shouldShowEmptyView() {

        every { localProvider.fetchCategories() } returns listOf()

        setupMockWebServer(SUCCESS_CATEGORY)

        setupMockWebServer(EMPTY_MEAL_BY_CATEGORY)

        activityRule.launchActivity(Intent())

        verify {
            mainActivityRobot
                .waitAlternativeView()
                .withEmptyImage()
                .withEmptyText()
                .clickInTryAgain()
        }
    }

    @Test
    fun shouldShowErrorView() {

        every { localProvider.fetchCategories() } returns listOf()

        setupMockWebServer(SUCCESS_CATEGORY)

        setupMockWebServer(EMPTY_MEAL_BY_CATEGORY, statusCode = 404)

        activityRule.launchActivity(Intent())

        verify {
            mainActivityRobot
                .waitAlternativeView()
                .withErrorImage()
                .withErrorText()
                .clickInTryAgain()
        }
    }
}