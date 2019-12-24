package com.example.banksalad.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class BaseApplication : Application(){
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}