package com.entregoya.entregouser.ui.payment.wallet

import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView


object AddMoneyToWalletContract {
    interface View : IBaseMvpView
    interface Presenter : IBaseMvpPresenter<View>
}