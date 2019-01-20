package br.com.tramalho.meal.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.meal.R
import br.com.tramalho.meal.databinding.FragmentMealDetailBinding
import br.com.tramalho.meal.utilities.Constants
import kotlinx.android.synthetic.main.detail_success_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealDetailFragment : Fragment() {

    private lateinit var dataBinding: FragmentMealDetailBinding

    val viewModel: MealDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_meal_detail, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        dataBinding.viewModel = viewModel

        arguments?.let { bundle ->

            val meal = bundle.getParcelable<Meal>(Constants.EXTRA_MEAL_DETAIL)
            viewModel.start(meal?.idMeal ?: "")
        }

        setupTabs()
        dataBinding.setLifecycleOwner(this)
        dataBinding.executePendingBindings()
    }

    private fun setupTabs() {

        val adapter = DetailFragmentPageAdapter(childFragmentManager)

        adapter.addFragment(InstructionFragment(), "Instrunction")
        adapter.addFragment(MealListFragment(), "Tile")
        adapter.addFragment(MealListFragment(), "Card")

        viewpager.adapter = adapter

        tabs.setupWithViewPager(viewpager)
    }
}
