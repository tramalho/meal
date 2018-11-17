package br.com.tramalho.data.infraestructure

sealed class Resource<out T>()
class Success<out T>(val data: T) : Resource<T>()
class Failure(val data: Error, val state: State = DataNotAvailable()) : Resource<Nothing>()

fun <T> Resource<T>.handle(actOnSuccess: Success<T>.() -> Unit, actOnFailure: Failure.() -> Unit = {}) {

    when (this) {
        is Success -> actOnSuccess()
        is Failure -> actOnFailure()
    }
}


