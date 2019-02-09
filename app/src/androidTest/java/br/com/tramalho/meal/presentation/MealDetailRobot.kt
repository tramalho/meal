package br.com.tramalho.meal.presentation

import br.com.tramalho.meal.R
import br.com.tramalho.meal.presentation.setup.BaseRobot

class MealDetailRobot : BaseRobot() {

    fun clickInstructionsTab(): MealDetailRobot {
        clickInText(R.string.instrunction)
        return this
    }

    fun checkFirstStep(): MealDetailRobot {
        matchText(R.string.step, "1")
        return this
    }
}