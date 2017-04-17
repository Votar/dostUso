package com.entregoya.entregouser.ui.history

import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView

object HistoryDeliveryContract {
    interface View : IBaseMvpView

    interface Presenter : IBaseMvpPresenter<View>

}