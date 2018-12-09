package br.com.tramalho.data.di

import android.content.Context
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module


val systemModule = module {

    factory { Moshi.Builder().build() }

    factory { androidContext().getSharedPreferences("PREF_KEY", Context.MODE_PRIVATE) }
}
