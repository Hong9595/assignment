package com.example.banksalad

import com.example.banksalad.data.repository.SharedPrefRepo
import com.example.banksalad.ui.display.DisplayContractor
import com.example.banksalad.ui.display.DisplayPresenter
import io.reactivex.Maybe
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DisplayFragmentUnitTest {
    private lateinit var presenter: DisplayPresenter
    @Mock
    lateinit var view: DisplayContractor.View
    @Mock
    lateinit var sharedPrefRepository: SharedPrefRepo
    private val result = "result"
    private val restoredVal = "restoredVal"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter = DisplayPresenter()
        
//        view = Mockito.mock(DisplayContractor.View)


        // 이 함수에 대한 값을 미리 정의
        Mockito.`when`(sharedPrefRepository.getCalculatedValue()).thenReturn(Maybe.just(restoredVal))
    }

    @Test
    fun getCalculatedVal(){
        // presenter에서 호출
        presenter.getSharedPrefData()
        // presenter.getSharedPrefData() 호출시에 -> repo.getCacluatedValue()가 call되고, return은 위에서 정의해줬음
        // 이에 대한 구독을 해줄때 view.showNumber(return value)이므로 verify를 통해 view함수가 호출되었는지 검사.
        Mockito.verify(view).showNumber(restoredVal)
    }


}