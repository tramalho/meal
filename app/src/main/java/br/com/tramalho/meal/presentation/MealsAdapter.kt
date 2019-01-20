package br.com.tramalho.meal.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.meal.R
import br.com.tramalho.meal.databinding.MealItemCardBinding
import br.com.tramalho.meal.presentation.MealsAdapter.MealViewHandler


class MealsAdapter(val onItemClickAction: (Meal) -> Unit) : ListAdapter<Meal, MealViewHandler>(MealDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHandler {

        val layoutInflater = LayoutInflater.from(parent.context)

        val itemBinding = DataBindingUtil.inflate<MealItemCardBinding>(
            layoutInflater,
            R.layout.meal_item_card, parent, false
        )

        return MealViewHandler(itemBinding)
    }

    override fun onBindViewHolder(holder: MealViewHandler, position: Int) = holder.bind(getItem(position))

    override fun getItemViewType(position: Int) = getItem(position).type

    inner class MealViewHandler(val binding: MealItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) = with(binding) {
            item = meal
            root.setOnClickListener { onItemClickAction(meal) }
            executePendingBindings()
        }
    }

    class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {

        override fun areItemsTheSame(
            oldItem: Meal,
            newItem: Meal
        ): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(
            oldItem: Meal,
            newItem: Meal
        ): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }
    }
}