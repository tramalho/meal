package br.com.tramalho.meal.presentation.setup

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matcher

fun verify(func: BaseRobot.() -> Unit) = BaseRobot().apply { func() }

open class BaseRobot {

    fun matchText(text: String) = checkIfIsDisplayed(withText(containsString(text)))

    fun matchText(textId: Int) = checkIfIsDisplayed(withText(textId))

    fun matchId(id: Int) = checkIfIsDisplayed(withId(id))

    fun matchDrawable(imageRId: Int, rDrawableId: Int) = onView(withId(imageRId))
        .check(matches(DrawableMatcher(rDrawableId)))

    fun click(rViewId : Int) = onView(withId(rViewId)).perform(ViewActions.click())

    private fun checkIfIsDisplayed(matcher: Matcher<View>) =
        onView(matcher).check(matches(isDisplayed()))

}