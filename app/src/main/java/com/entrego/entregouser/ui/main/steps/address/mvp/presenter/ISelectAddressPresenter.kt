package com.entrego.entregouser.ui.main.steps.address.mvp.presenter

import com.entrego.entregouser.ui.main.steps.address.mvp.view.ISelectAddressView


interface ISelectAddressPresenter {

    fun onStart(view: ISelectAddressView)
    fun onStop()

}