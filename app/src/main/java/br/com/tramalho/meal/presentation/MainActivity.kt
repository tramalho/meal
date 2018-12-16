package br.com.tramalho.meal.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import br.com.tramalho.meal.R
import br.com.tramalho.meal.databinding.ActivityMainBinding
import br.com.tramalho.meal.presentation.ListStatus.Companion.HEADER
import br.com.tramalho.meal.utilities.doObserve
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    val viewModel: MealViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        dataBinding.viewModel = viewModel
        dataBinding.setLifecycleOwner(this)
        dataBinding.executePendingBindings()

        val gridLayoutManager = GridLayoutManager(this, 2)

        gridLayoutManager.spanSizeLookup = SpanSizeLookup()

        mealRecyclerView.layoutManager = gridLayoutManager

        mealRecyclerView.adapter = MealsAdapter(arrayListOf())

        setupListeners()

        viewModel.start()
    }

    private fun setupListeners() {
        viewModel.dataReceived.doObserve(this, Observer {
            val mealsAdapter = mealRecyclerView.adapter as MealsAdapter
            mealsAdapter.meals.addAll(it)
            mealsAdapter.notifyDataSetChanged()
        })

        mealRecyclerView.loadMore { viewModel.fetchMeals() }
    }

    private inner class SpanSizeLookup : GridLayoutManager.SpanSizeLookup() {
        private val spanValue = 2

        override fun getSpanSize(position: Int): Int {

            val adapter = mealRecyclerView.adapter

            return when {
                position == BigDecimal.ZERO.toInt() -> spanValue
                adapter?.getItemViewType(position) == HEADER -> spanValue
                else -> BigDecimal.ONE.toInt()
            }
        }
    }
}
