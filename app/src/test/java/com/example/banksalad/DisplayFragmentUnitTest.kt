package com.example.banksalad

import com.example.banksalad.data.repository.SharedPrefRepo
import com.example.banksalad.event.EventBus
import com.example.banksalad.ui.display.DisplayContractor
import com.example.banksalad.ui.display.DisplayPresenter
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception

class DisplayFragmentUnitTest {
    private lateinit var presenter: DisplayPresenter
    @Mock
    lateinit var view: DisplayContractor.View
    @Mock
    lateinit var sharedPrefRepository: SharedPrefRepo
    @Mock
    lateinit var eventBus: EventBus

    private val result = "result"
    private val restoredVal = "restoredVal"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter = DisplayPresenter()
        presenter.attachView(view)

        // 이 함수에 대한 값을 미리 정의
//        Mockito.`when`(sharedPrefRepository.getCalculatedValue()).thenReturn(Maybe.just(restoredVal))
//        Mockito.`when`(eventBus.send("2"))
//        Mockito.doThrow(Exception()).doNothing().`when`(eventBus.send("2"))
//        Mockito.doThrow(Exception()).doNothing().`when`(eventBus).send("2")
        Mockito.doNothing().`when`(eventBus).send("2")
        Mockito.doCallRealMethod().`when`(eventBus).send("2")
//        Mockito.`when`(eventBus.send("2")).thenReturn(Unit)
    }

    @Test
    fun getCalculatedVal() {
        presenter.getInputNumber()
        Mockito.verify(view).showNumber("2")


//        then(view).should().showNumber(result)

//        // presenter에서 호출
//        presenter.getSharedPrefData()
        // presenter.getSharedPrefData() 호출시에 -> repo.getCacluatedValue()가 호출되고, return은 위에서 정의해줬음
        // onSuccess에서 view.showNumber(restoredVal)이므로 verify를 통해 view함수가 호출되었는지 검사.
//        Mockito.verify(view).showNumber(restoredVal)
        //        Assert.assertEquals(restoredVal,"")
    }

    @Test
    fun calculate() {

    }

}