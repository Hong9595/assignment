package com.example.banksalad.ui.calculate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.banksalad.R
import com.example.banksalad.event.EventBus
import kotlinx.android.synthetic.main.fragment_calculate.*

class CalculateFragment : Fragment() , View.OnClickListener{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_calculate, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onClick(view: View?) {
        val temp = view as Button
        EventBus.send(temp.text.toString())
    }

    fun initButton(){
        plusBtn.setOnClickListener(this)
        minusBtn.setOnClickListener(this)
        multiplyBtn.setOnClickListener(this)
        modularBtn.setOnClickListener(this)
        clearBtn.setOnClickListener(this)
        resultBtn.setOnClickListener(this)
        zeroBtn.setOnClickListener(this)
        oneBtn.setOnClickListener(this)
        twoBtn.setOnClickListener(this)
    }

    // 여기서 뭐 리스너 등록해서 버튼 눌릴때마다 bus.onNext
    // display쪽에서는 그거 구독.
}