package br.com.tramalho.meal.presentation

import android.app.Application
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tramalho.meal.R

abstract class BaseViewModel(protected val application: Application) : ViewModel() {

    protected enum class ViewState { SUCCESS, ERROR, LOADING, NO_DATA }

    val loading = MutableLiveData<Int>().apply { value = VISIBLE }
    val alternativePageVisibility = MutableLiveData<Int>().apply { value = GONE }
    val tryAgainVisibility = MutableLiveData<Int>().apply { value = GONE }
    val statusImage = MutableLiveData<Int>().apply { value = R.drawable.ic_artichoke }
    val message = MutableLiveData<String>().apply { value = "" }

    protected fun setupViewState(viewState: ViewState): Status {

        return when (viewState) {
            ViewState.LOADING -> Status(VISIBLE, GONE)
            ViewState.SUCCESS -> Status(GONE, VISIBLE)
            ViewState.ERROR -> Status(GONE, GONE, VISIBLE, GONE, R.drawable.ic_artichoke)
            ViewState.NO_DATA -> Status(
                GONE, GONE, GONE, VISIBLE, R.drawable.ic_empty_dishes,
                R.string.empty_msg_try_again
            )
        }
    }

    open protected fun configVisibility(viewState: ViewState) {

        val result = setupViewState(viewState)

        val hasOneVisible = intArrayOf(result.emptyView, result.errorView).contains(VISIBLE)

        alternativePageVisibility.value = if (hasOneVisible) VISIBLE else GONE

        loading.value = result.loading

        tryAgainVisibility.value = alternativePageVisibility.value

        statusImage.value = result.rIdImage

        message.value = application.getString(result.message)
    }

    abstract fun tryAgain()

    data class Status(
        val loading: Int,
        val showData: Int,
        val errorView: Int = GONE,
        val emptyView: Int = GONE,
        val rIdImage: Int = R.drawable.ic_artichoke,
        val message: Int = R.string.error_msg_try_again
    )
}