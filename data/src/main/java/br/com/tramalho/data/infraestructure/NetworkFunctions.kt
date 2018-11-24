package br.com.tramalho.data.infraestructure

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Response

fun <T> callAsync(block: () -> Deferred<Response<T>>) = GlobalScope.async {

    try {

        val response = block().await()

        if (response.isSuccessful) {
            Success(response.body()!!)
        } else {
            Log.d(this.javaClass.canonicalName, response.errorBody().toString())
            Failure(Error(response.errorBody().toString()))
        }
    } catch (e: Exception) {
        Failure(Error(e.message), UnexpectedError())
    }
}

