package br.com.tramalho.meal.presentation

import android.app.Application
import br.com.tramalho.data.di.networkModule
import br.com.tramalho.data.di.provideModule
import br.com.tramalho.data.di.serviceModule
import br.com.tramalho.meal.di.*
import org.koin.android.ext.android.startKoin


class MealApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this, listOf(
                viewModelModule,
                businessModule,
                provideModule,
                serviceModule,
                networkModule
            )
        )
    }

}