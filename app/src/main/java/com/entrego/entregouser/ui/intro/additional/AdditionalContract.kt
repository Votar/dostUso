package com.entrego.entregouser.ui.intro.additional

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView

interface AdditionalContract {
    interface View : IBaseMvpView
    interface Presenter : IBaseMvpPresenter<View>
}