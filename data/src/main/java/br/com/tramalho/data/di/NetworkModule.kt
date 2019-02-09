package br.com.tramalho.data.di

import br.com.tramalho.data.infraestructure.RetrofitFactory
import org.koin.dsl.module.module
import retrofit2.converter.moshi.MoshiConverterFactory


val networkModule = module(override = true) {

    factory {
        RetrofitFactory.makeServiceBuilder(get(), MoshiConverterFactory.create())
    }

    factory("RetrofitMealDetailAdapter") {
        val moshiConverterFactory = MoshiConverterFactory.create(get("MealDetailJsonAdapter"))
        RetrofitFactory.makeServiceBuilder(get(), moshiConverterFactory)
    }
}


