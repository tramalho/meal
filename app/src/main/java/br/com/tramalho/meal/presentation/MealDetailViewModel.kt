package br.com.tramalho.meal.presentation

import android.app.Application
import android.view.View.GONE
import androidx.lifecycle.MutableLiveData
import br.com.tramalho.data.entity.meal.MealDetail
import br.com.tramalho.data.infraestructure.handle
import br.com.tramalho.domain.business.MealDetailBusiness
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MealDetailViewModel(
    private val mealDetailBusiness: MealDetailBusiness,
    application: Application,
    private val coroutineScope: CoroutineScope
) : BaseViewModel(application) {

    val dataVisibility = MutableLiveData<Int>().apply { GONE }
    val mealDetails = MutableLiveData<MealDetail>()
    private lateinit var idMeal: String

    override fun tryAgain() {
        start(idMeal)
    }

    fun start(idMeal: String) {

        this.idMeal = idMeal

        coroutineScope.launch {

            configVisibility(ViewState.LOADING)

            mealDetailBusiness
                .fetchDetails(idMeal)
                .handle({
                    mealDetails.value = this.data
                    configVisibility(ViewState.SUCCESS)
                }, {
                    configVisibility(ViewState.ERROR)
                })
        }
    }

    override fun configVisibility(viewState: ViewState) {

        super.configVisibility(viewState)
        val result = setupViewState(viewState)

        dataVisibility.value = result.showData
    }
}