package br.com.tramalho.data.di

import br.com.tramalho.data.provider.LocalProvider
import br.com.tramalho.data.provider.MealDetailProvider
import br.com.tramalho.data.provider.MealProvider
import org.koin.dsl.module.module

val provideModule = module {

    factory { LocalProvider(get(), get()) }

    factory { MealProvider(get()) }

    factory { MealDetailProvider(get()) }
}