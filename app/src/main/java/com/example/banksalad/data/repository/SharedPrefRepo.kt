package com.example.banksalad.data.repository

import com.example.banksalad.data.DataSource
import com.example.banksalad.data.source.SharedPrefSource
import io.reactivex.Completable
import io.reactivex.Maybe

object SharedPrefRepo : DataSource{
    override fun getCalculatedValue(): Maybe<String> = SharedPrefSource.getCalculatedValue()

    override fun setCalculatedValue(result: String): Completable  = SharedPrefSource.setCalculatedValue(result)
}