package br.com.tramalho.meal.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import br.com.tramalho.meal.R
import br.com.tramalho.meal.utilities.doObserve
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel: MealViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayoutManager = GridLayoutManager(this, 2)

        gridLayoutManager.spanSizeLookup = SpanSizeLookup()

        mealsResyclerView.layoutManager = gridLayoutManager

        mealsResyclerView.adapter = MealsAdapter(arrayListOf())

        setupListeners()

        viewModel.start()
    }

    private fun setupListeners() {
        viewModel.dataReceived.doObserve(this, Observer {
            val mealsAdapter = mealsResyclerView.adapter as MealsAdapter
            mealsAdapter.meals.addAll(it)
            mealsAdapter.notifyDataSetChanged()
        })

        viewModel.error.doObserve(this, Observer {
            Toast.makeText(this@MainActivity, "Algo deu errado", Toast.LENGTH_SHORT).show()
        })
    }

    private inner class SpanSizeLookup : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {

            return when {
                position == 0 -> 2
                viewModel.changeCategory() -> 2
                else -> 1
            }
        }
    }
}
