package com.entrego.entregouser.ui.history

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView

object HistoryDeliveryContract {
    interface View : IBaseMvpView

    interface Presenter : IBaseMvpPresenter<View>

}