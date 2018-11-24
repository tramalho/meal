package br.com.tramalho.meal.presentation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import br.com.tramalho.meal.R
import br.com.tramalho.meal.databinding.ActivityMainBinding
import br.com.tramalho.meal.presentation.ListStatus.Companion.HEADER
import br.com.tramalho.meal.utilities.doObserve
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        mealsResyclerView.loadMore { viewModel.fetchMeals() }

        val avd = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_loading)
        val iv = animation.apply {
            setImageDrawable(avd)
        }
        avd?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                iv.post { avd.start() }
            }
        })
        avd?.start()
    }

    private inner class SpanSizeLookup : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {

            val adapter = mealsResyclerView.adapter

            return when {
                position == 0 -> 2
                adapter?.getItemViewType(position) == HEADER -> 2
                else -> 1
            }
        }
    }
}
