package com.entrego.entregouser.ui.payment.card

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView


object AddCardContract {
    interface View : IBaseMvpView

    interface Presenter : IBaseMvpPresenter<View>
}