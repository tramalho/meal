package br.com.tramalho.data.di

import br.com.tramalho.data.BuildConfig
import br.com.tramalho.data.infraestructure.RetrofitFactory
import org.koin.dsl.module.module


val networkModule = module {

    factory {
        RetrofitFactory.makeServiceBuilder(BuildConfig.BASE_URL)
    }
}


