package br.com.tramalho.meal.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tramalho.meal.R
import kotlinx.android.synthetic.main.fragment_instruction.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstructionFragment : Fragment() {

    val viewModel: MealDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_instruction, container, false)
    }

    override fun onResume() {
        super.onResume()

        val mealInstrunctionAdapter = MealInstrunctionAdapter(arrayListOf())

        mealInstructionList.adapter = mealInstrunctionAdapter

        mealInstructionList.layoutManager = LinearLayoutManager(context)

        viewModel.mealDetails.observe(this, Observer {
            mealInstrunctionAdapter.update(it.strInstructions)
        })
    }
}
