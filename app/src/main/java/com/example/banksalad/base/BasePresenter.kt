package com.example.banksalad.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T> {
    var view: T? = null
        protected set

    protected val compositeDisposable = CompositeDisposable()

    fun attachView(view: T){
        this.view = view
    }

    fun detachView(){
        if(!compositeDisposable.isDisposed){
            compositeDisposable.clear()
        }
        this.view = null
    }

}