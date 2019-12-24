package com.example.banksalad.ui.display

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.banksalad.R
import com.example.banksalad.base.BaseApplication
import com.example.banksalad.data.repository.SharedPrefRepo
import kotlinx.android.synthetic.main.fragment_calculate.*
import kotlinx.android.synthetic.main.fragment_display.*
import java.lang.Integer.parseInt

class DisplayFragment : Fragment(), DisplayContractor.View{
    private val displayPresenter by lazy{
        DisplayPresenter()
    }
    private var calculateCount = 0
    private var prevNumber: String = ""
    private var prevOperator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(BACKUP_CALCULATION_RESULT,displayContent.text.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_display, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState == null){
            // savedInstanceState == NULL 이라면 repo -> local datasource에서data 가져오기
            // presenter.getData() -> Repository.getData()
            displayPresenter.getSharedPrefData()
        } else {
            displayContent.text = savedInstanceState.getString(BACKUP_CALCULATION_RESULT)
        }

        displayPresenter.attachView(this)
        displayPresenter.getInputNumber()
    }

    override fun showNumber(enteredNumber: String) {
        displayContent.text = enteredNumber
    }

    override fun getDisplayNumber(): String = displayContent.text.toString()

    override fun showToast(str: String) {
        Toast.makeText(activity,str,Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        displayPresenter.setSharedPrefData(getDisplayNumber())
    }

    override fun onDestroy() {
        super.onDestroy()
        displayPresenter.detachView()
    }

    companion object{
        const val BACKUP_CALCULATION_RESULT = "backup_calculation_result"
    }


}