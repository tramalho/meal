package br.com.tramalho.meal.presentation

import android.app.Application
import br.com.tramalho.data.di.*
import br.com.tramalho.meal.di.businessModule
import br.com.tramalho.meal.di.viewModelModule
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
                networkModule,
                urlModule,
                systemModule
            )
        )
    }
}