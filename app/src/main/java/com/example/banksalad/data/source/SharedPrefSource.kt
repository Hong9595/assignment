package com.example.banksalad.data.source

import android.util.Log
import com.example.banksalad.base.BaseApplication
import com.example.banksalad.data.DataSource
import com.example.banksalad.ui.display.DisplayFragment
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

object SharedPrefSource : DataSource{
    const val SHARED_PREF_KEY = "shared_pref_key"
    val pref = BaseApplication.appContext?.getSharedPreferences(SHARED_PREF_KEY,0)
    val editor = pref?.edit()
    override fun getCalculatedValue(): Maybe<String> {
        return Maybe.create { emitter ->
            try{
                val result = pref?.getString(DisplayFragment.BACKUP_CALCULATION_RESULT,"0")
                if(result == null){
                    emitter.onComplete()
                } else {
                    emitter.onSuccess(result)
                }
            } catch (t: ClassCastException) {
                emitter.onError(t)
            }
        }
    }

    override fun setCalculatedValue(result: String): Completable {
        return Completable.create { emitter ->
            editor?.putString(DisplayFragment.BACKUP_CALCULATION_RESULT, result)
            editor?.apply()
            emitter.onComplete()
        }
    }

}