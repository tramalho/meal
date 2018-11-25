package br.com.tramalho.data.infraestructure

sealed class NetworkState()
class DataNotAvailable : NetworkState()
class UnexpectedError : NetworkState()
