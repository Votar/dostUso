package com.entrego.entregouser.ui.payment.wallet

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView


object AddMoneyToWalletContract {
    interface View : IBaseMvpView
    interface Presenter : IBaseMvpPresenter<View>
}