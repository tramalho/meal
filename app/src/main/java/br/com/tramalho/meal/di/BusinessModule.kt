package br.com.tramalho.meal.di

import br.com.tramalho.domain.business.MealDetailBusiness
import br.com.tramalho.domain.business.MealListBusiness
import org.koin.dsl.module.module

val businessModule = module {

    factory { MealListBusiness(get(), get()) }

    factory { MealDetailBusiness(get()) }
}
