package br.com.tramalho.data.di

import br.com.tramalho.data.infraestructure.ServiceFactory
import org.koin.dsl.module.module


val serviceModule = module {

    factory { ServiceFactory(get()).makeMealService() }
}
