package br.com.tramalho.meal.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.tramalho.meal.R
import br.com.tramalho.meal.databinding.MealInstrunctionItemBinding


class MealInstrunctionAdapter(var item: ArrayList<String>) : Adapter<MealInstrunctionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val itemBinding = DataBindingUtil.inflate<MealInstrunctionItemBinding>(
            layoutInflater, R.layout.meal_instrunction_item, parent, false
        )

        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item[position], position)
    }

    fun update(strInstructions: List<String>) {
        item.addAll(strInstructions)
        notifyDataSetChanged()
    }


    class ViewHolder(private var binding: MealInstrunctionItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String, position: Int) {
            with(binding) {
                title = binding.root.context.getString(R.string.step, (1 + position).toString())
                body = text
                binding.executePendingBindings()
            }
        }
    }
}