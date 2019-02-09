package br.com.tramalho.data.di

import android.content.Context
import br.com.tramalho.data.infraestructure.MealDetailJsonAdapter
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module


val systemModule = module {

    factory("Default") { Moshi.Builder().build() }

    factory("MealDetailJsonAdapter") { Moshi.Builder().add(MealDetailJsonAdapter()).build() }

    factory { androidContext().getSharedPreferences("PREF_KEY", Context.MODE_PRIVATE) }
}
