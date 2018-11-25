package br.com.tramalho.data.infraestructure


fun String.toAlphaNumeric() = this.replace(Regex("[^A-Za-z0-9 ]"), "")