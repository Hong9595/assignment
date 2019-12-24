package com.example.banksalad.data

import io.reactivex.Completable
import io.reactivex.Maybe


interface DataSource {
    fun getCalculatedValue(): Maybe<String>
    fun setCalculatedValue(result: String): Completable
}