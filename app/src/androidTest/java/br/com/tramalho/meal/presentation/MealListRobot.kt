package br.com.tramalho.meal.presentation

import br.com.tramalho.meal.R
import br.com.tramalho.meal.presentation.setup.BaseRobot
import br.com.tramalho.meal.presentation.setup.Constants.Companion.FIRST_ITEM_NAME

class MealListRobot : BaseRobot() {

    fun withEmptyImage(): MealListRobot {
        matchDrawable(R.id.imageStatus, R.drawable.ic_empty_dishes)
        return this
    }
    fun withErrorImage(): MealListRobot {
        matchDrawable(R.id.imageStatus, R.drawable.ic_artichoke)
        return this
    }

    fun withEmptyText(): MealListRobot {
        matchText(R.string.empty_msg_try_again)
        return this
    }

    fun withErrorText(): MealListRobot {
        matchText(R.string.error_msg_try_again)
        return this
    }

    fun clickInTryAgain(): MealListRobot {
        click(R.id.tryAgainButton)
        return this
    }

    fun waitAlternativeView(): MealListRobot {
        Thread.sleep(1000)
        matchId(R.id.imageStatus)
        return this
    }

    fun waitSuccessView(): MealListRobot {
        Thread.sleep(1000)
        matchId(R.id.mealRecyclerView)
        return this
    }

    fun clickFirstMeal(): MealListRobot {
        clickInText(FIRST_ITEM_NAME)
        return this
    }
}