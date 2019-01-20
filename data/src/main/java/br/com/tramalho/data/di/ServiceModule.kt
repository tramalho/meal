package br.com.tramalho.data.di

import br.com.tramalho.data.infraestructure.ServiceFactory
import org.koin.dsl.module.module


val serviceModule = module(override = true) {

    factory { ServiceFactory(get()).makeMealService() }

    factory { ServiceFactory(get("RetrofitMealDetailAdapter")).makeMealService() }
}
