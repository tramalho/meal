package br.com.tramalho.meal.presentation

import br.com.tramalho.meal.R
import br.com.tramalho.meal.presentation.setup.BaseRobot

class MainActivityRobot : BaseRobot() {

    fun withEmptyImage(): MainActivityRobot {
        matchDrawable(R.id.imageStatus, R.drawable.ic_empty_dishes)
        return this
    }
    fun withErrorImage(): MainActivityRobot {
        matchDrawable(R.id.imageStatus, R.drawable.ic_artichoke)
        return this
    }

    fun withEmptyText(): MainActivityRobot {
        matchText(R.string.empty_msg_try_again)
        return this
    }

    fun withErrorText(): MainActivityRobot {
        matchText(R.string.error_msg_try_again)
        return this
    }

    fun clickInTryAgain(): MainActivityRobot {
        click(R.id.tryAgainButton)
        return this
    }

    fun checkLoading(): MainActivityRobot {
        matchId(R.id.vectorLoading)
        return this
    }

    fun waitAlternativeView(): MainActivityRobot {
        Thread.sleep(1000)
        matchId(R.id.imageStatus)
        return this
    }

    fun waitSuccessView(): MainActivityRobot {
        Thread.sleep(1000)
        matchId(R.id.mealRecyclerView)
        return this
    }
}