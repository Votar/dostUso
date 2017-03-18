package com.entrego.entregouser.ui.payment

import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter


class PaymentMethodPresenter : PaymentMethodContract.Presenter,
        BaseMvpPresenter<PaymentMethodContract.View>()