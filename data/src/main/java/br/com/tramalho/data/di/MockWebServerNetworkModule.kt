package br.com.tramalho.data.di

import br.com.tramalho.data.infraestructure.RetrofitFactory
import org.koin.dsl.module.module

val mockWebServerNeworkModule = module(override = true) {

    factory {
        RetrofitFactory.makeServiceBuilder("http://localhost:51384")
    }
}
