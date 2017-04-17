package com.entregoya.entregouser.ui.intro.additional

import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView

interface AdditionalContract {
    interface View : IBaseMvpView
    interface Presenter : IBaseMvpPresenter<View>
}