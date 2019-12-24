package com.example.banksalad.ui.display

import android.util.Log
import com.example.banksalad.base.BasePresenter
import com.example.banksalad.data.repository.SharedPrefRepo
import com.example.banksalad.event.EventBus
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_display.*
import java.util.*

/**
 * @ acc : 결괏값
 * @ operatorFlag : 연산 기호를 눌렀는지에 대한 flag
 * @ lastOperator : 마지막으로 입력한 연산자
 * @ lastNumber : 마지막으로 입력한 값
 * @ resultForIntent : 다음 액티비티로 넘겨줄 식
 */
class DisplayPresenter : BasePresenter<DisplayContractor.View>(), DisplayContractor.Presenter{
    private var acc: Int = -1
    private var opeartorFlag = false
    private var lastOperator =""
    private var lastNumber = "0"
    private var resultForIntent = ""

    override fun getInputNumber() {
        compositeDisposable += EventBus.observe()
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = { input ->
                    var displayNumber = view?.getDisplayNumber()
                    when(input){
                        "0","1","2" -> {
                            // 연산자 누르고 숫자 누르는 경우 -> 새로 입력 받음
                            if(opeartorFlag) {
                                displayNumber = "0"
                            }
                            // 값이 0일때 숫자 입력하면 0 지우고 숫자 출력
                            if(displayNumber == "0"){
                                view?.showNumber(input)
                            } else { // 값이 0이 아닌경우에는 concat
                                view?.showNumber(displayNumber + input)
                            }
                            // 숫자 눌렀으므로 연산자 기호는 안 누른것으로 초기화
                            opeartorFlag = false
                        }
                        "+","-","x","%" -> {
                            // 숫자 누른 뒤 연산자를 누른 경우
                            if(!opeartorFlag){
                                // acc == -1 이라면 아직 입력 안 받은 경우
                                // != -1 이면 이미 입력 받은 경우이므로 새로운 입력과 함께 연산 수행
                                if(acc != -1){
                                    Log.d("PreseterTest","LastOperator is: "+ lastOperator + " acc is :" + acc + " lastNumber is: " + displayNumber)
                                    val result = calculate(lastOperator, acc , displayNumber!!.toInt())
                                    acc = result.toInt()
                                    lastNumber = displayNumber
                                    view?.showNumber(result)
                                    Log.d("PreseterTest","calculate is performed and result : " + result)
                                } else { // 첫 입력
                                    displayNumber?.let {
                                        acc = it.toInt()
                                        Log.d("PreseterTest","Queue push. Push num is: " + it)
                                    }
                                }
                            }
                            // 연산자를 눌렀으므로 ture로 초기화
                            opeartorFlag = true
                            lastOperator = input
                        }

                        "=" -> {
                            var result: String
                            acc.let {
                                // 아무 입력없이 =만 누른 경우
                                if(it == -1)    result = "0"
                                else{
                                    // opeartorFlag가 false => 숫자 누르고 = 을 누른 경우이므로 lastNumber가 화면에 입력한 값이 됩니다
                                    if(!opeartorFlag){
                                        displayNumber?.let{
                                            lastNumber = it
                                        }
                                    }
                                    Log.d("PreseterTest=","LastOperator is: "+ lastOperator + " acc is :" + acc + " lastNumber is: " + displayNumber)
                                    result = calculate(lastOperator,acc,lastNumber.toInt())
                                    acc = result.toInt()
                                }
                            }

                            Log.d("PreseterTest=","result is: "+ result)
                            view?.showNumber(result)
                            opeartorFlag = true
                        }
                        // 전체 초기화
                        "C" -> {
                            lastOperator = ""
                            lastNumber = "0"
                            acc = -1
                            view?.showNumber("0")
                            opeartorFlag = true
                            // operatorFlag == false로 해줘도 어차피 다른 연산자 누를시에 display가 0이라 상관 없을듯.
                        }
                        else -> {}
                    }
                },
                onComplete = {},
                onError = { }
            )
    }

    override fun getSharedPrefData() {
        compositeDisposable += SharedPrefRepo.getCalculatedValue()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { view?.showNumber(it) },
                onComplete = {},
                onError = {}
            )
    }

    override fun setSharedPrefData(str: String) {
        compositeDisposable += SharedPrefRepo.setCalculatedValue(str)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {},
                onError = {}
            )
    }

    fun calculate(lastOperator: String, value1: Int, value2: Int): String {
        when(lastOperator){
            "+" -> { return (value1 + value2).toString()}
            "-" -> { return (value1 - value2).toString()}
            "x" -> { return (value1 * value2).toString()}
            "%" -> {
                if(value2 == 0) {
                    view?.showToast("% 0 은 불가능합니다.")
                    return "0"
                }
                else return (value1 % value2).toString()
            }
            else -> {
                return "0"
            }
//            "C" -> { return "0" }
//            "%" -> { return (value1 + value2 / 100).toString()}
        }
    }
}