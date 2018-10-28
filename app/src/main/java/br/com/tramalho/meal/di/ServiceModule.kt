package br.com.tramalho.meal.di

import br.com.tramalho.data.infraestructure.RetrofitFactory
import org.koin.dsl.module.module


val serviceModule = module {

    factory { RetrofitFactory.makeMealService() }
}
