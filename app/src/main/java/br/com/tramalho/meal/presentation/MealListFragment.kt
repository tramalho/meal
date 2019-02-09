package br.com.tramalho.meal.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.meal.R
import br.com.tramalho.meal.databinding.FragmentMealListBinding
import br.com.tramalho.meal.utilities.Constants
import br.com.tramalho.meal.utilities.loadMore
import kotlinx.android.synthetic.main.fragment_meal_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal

const val spanValue = 2

class MealListFragment : Fragment() {

    val viewModel: MealViewModel by viewModel()

    private lateinit var dataBinding: FragmentMealListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_meal_list, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        dataBinding.viewModel = viewModel
        dataBinding.setLifecycleOwner(this)
        dataBinding.executePendingBindings()

        val gridLayoutManager = GridLayoutManager(context, spanValue)

        gridLayoutManager.spanSizeLookup = SpanSizeLookup()

        dataBinding.mealRecyclerView.layoutManager = gridLayoutManager

        val clickAction = { meal: Meal ->

            val bundle = Bundle().apply { putParcelable(Constants.EXTRA_MEAL_DETAIL, meal) }

            view.findNavController().navigate(R.id.actionMealListToDetail, bundle)
        }

        dataBinding.mealRecyclerView.adapter = MealsAdapter(clickAction)

        setupListeners()

        viewModel.start()
    }

    private fun setupListeners() {
        viewModel.dataReceived.observe(viewLifecycleOwner, Observer {
            val mealsAdapter = mealRecyclerView.adapter as MealsAdapter
            mealsAdapter.submitList(it)
        })

        mealRecyclerView.loadMore { viewModel.fetchMeals() }
    }

    private inner class SpanSizeLookup : GridLayoutManager.SpanSizeLookup() {

        override fun getSpanSize(position: Int): Int {

            val adapter = mealRecyclerView.adapter

            return when {
                position == BigDecimal.ZERO.toInt() -> spanValue
                adapter?.getItemViewType(position) == ListStatus.HEADER -> spanValue
                else -> BigDecimal.ONE.toInt()
            }
        }
    }
}
