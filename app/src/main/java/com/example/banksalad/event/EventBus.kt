package com.example.banksalad.event

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

object EventBus{
    val bus = PublishSubject.create<String>()

    fun send(str: String){
        bus.onNext(str)
    }

    fun observe() =
        bus.observeOn(AndroidSchedulers.mainThread())


}