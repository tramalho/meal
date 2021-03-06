package br.com.tramalho.meal.di

import br.com.tramalho.data.infraestructure.UI
import br.com.tramalho.meal.presentation.MealDetailViewModel
import br.com.tramalho.meal.presentation.MealViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel { MealViewModel(get(), get(), UI) }

    single { MealDetailViewModel(get(), get(),  UI) }

}

