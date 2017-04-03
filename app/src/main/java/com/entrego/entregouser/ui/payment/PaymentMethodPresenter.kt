package com.entrego.entregouser.ui.payment

import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.EntregoStorage


class PaymentMethodPresenter : PaymentMethodContract.Presenter,
        BaseMvpPresenter<PaymentMethodContract.View>() {
    val mToken = EntregoStorage.getTokenOrEmpty()

    fun requestCards() {

    }
}