package br.com.tramalho.meal.data.infraestructure

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@kotlinx.coroutines.ExperimentalCoroutinesApi
val UI : CoroutineDispatcher = Dispatchers.Unconfined