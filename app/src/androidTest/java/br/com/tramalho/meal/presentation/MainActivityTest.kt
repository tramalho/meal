package br.com.tramalho.meal.presentation

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.tramalho.meal.presentation.setup.BaseInstrumentedTest
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

    @Test
    fun shouldShowListOfItens() {

        every { localProvider.fetchCategories() } returns listOf()

        setupSuccessMock(SUCCESS_CATEGORY)

        setupSuccessMock(SUCCESS_MEAL_BY_CATEGORY)

        activityRule.launchActivity(Intent())

        verify {
            matchText("BeefandMustardPie")
        }
    }
}