package br.com.tramalho.data.infraestructure

import android.util.Log
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async
import retrofit2.Response

fun <T> callAsync(block: () -> Deferred<Response<T>>) = GlobalScope.async {

    val response = block().await()

    if (response.isSuccessful) {
        Success(response.body()!!)
    } else {
        Log.d(this.javaClass.canonicalName, response.errorBody().toString())
        Failure(Error(response.errorBody().toString()))
    }
}
