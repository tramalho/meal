package br.com.tramalho.data.infraestructure

sealed class State()
class DataNotAvailable : State()
class UnexpectedError : State()
