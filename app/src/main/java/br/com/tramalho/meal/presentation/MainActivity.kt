package br.com.tramalho.meal.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.tramalho.meal.R
import br.com.tramalho.meal.utilities.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel: MealViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.dataReceived.observe(this, Observer {
            Toast.makeText(this@MainActivity, "${it.size}", Toast.LENGTH_SHORT).show();
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this@MainActivity, "Algo deu errado", Toast.LENGTH_SHORT).show();
        })

        viewModel.start(this)
    }
}
