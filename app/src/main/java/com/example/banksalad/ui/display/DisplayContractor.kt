package com.example.banksalad.ui.display

interface DisplayContractor {
    interface View {
        fun showNumber(enteredNumber: String)
        fun getDisplayNumber(): String
        fun showToast(str: String)
    }

    interface Presenter {
        fun getInputNumber()
        fun getSharedPrefData()
        fun setSharedPrefData(str: String)
    }
}