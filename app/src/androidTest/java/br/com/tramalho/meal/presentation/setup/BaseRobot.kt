package br.com.tramalho.meal.presentation.setup

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.containsString

fun verify(func: BaseRobot.() -> Unit) = BaseRobot().apply { func() }

open class BaseRobot {

    fun matchText(text: String) =
        onView(
            withText(containsString(text))
        )
            .check(matches(isDisplayed()))
}