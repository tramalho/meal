package br.com.tramalho.data.di

import br.com.tramalho.data.BuildConfig
import org.koin.dsl.module.module

val urlModule = module {
    factory { BuildConfig.BASE_URL }
}