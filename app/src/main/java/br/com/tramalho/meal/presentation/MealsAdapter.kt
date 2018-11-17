package br.com.tramalho.meal.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.tramalho.data.entity.meal.Meal
import br.com.tramalho.meal.R
import br.com.tramalho.meal.presentation.MealsAdapter.MealViewHandler
import kotlinx.android.synthetic.main.meal_item_card.view.*


class MealsAdapter(val meals : ArrayList<Meal>) : RecyclerView.Adapter<MealViewHandler>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHandler {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_item_card, parent, false)

        return MealViewHandler(view)
    }

    override fun getItemCount() = meals.size

    override fun onBindViewHolder(holder: MealViewHandler, position: Int) = holder.bind(meals[position])


    class MealViewHandler(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(meal : Meal) {
            itemView.mealItemTitle.text = meal.strMeal
        }
    }
}