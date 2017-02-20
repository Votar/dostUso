package com.entrego.entregouser.ui.profile.payment

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView

object PaymentMethodContract {
    interface View : IBaseMvpView{
        fun showAddCardActivity()
        fun showAddMoneyToWallet()
    }
    interface Presenter: IBaseMvpPresenter<View>{

    }
}