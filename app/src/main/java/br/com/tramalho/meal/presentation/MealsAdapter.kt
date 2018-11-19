package br.com.tramalho.meal.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.meal.R
import br.com.tramalho.meal.databinding.MealItemCardBinding
import br.com.tramalho.meal.presentation.MealsAdapter.MealViewHandler


class MealsAdapter(val meals : ArrayList<Meal>) : RecyclerView.Adapter<MealViewHandler>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHandler {

        val layoutInflater = LayoutInflater.from(parent.context)

        val itemBinding = DataBindingUtil.inflate<MealItemCardBinding>(layoutInflater,
            R.layout.meal_item_card, parent, false)

        return MealViewHandler(itemBinding)
    }

    override fun getItemCount() = meals.size

    override fun onBindViewHolder(holder: MealViewHandler, position: Int) = holder.bind(meals[position])


    class MealViewHandler(val binding: MealItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meal : Meal) {
            binding.item = meal
            binding.executePendingBindings()
        }
    }
}