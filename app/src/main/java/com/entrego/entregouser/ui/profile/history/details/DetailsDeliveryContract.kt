package com.entrego.entregouser.ui.profile.history.details

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView

interface DetailsDeliveryContract {
    interface View : IBaseMvpView{

    }

    interface Presenter : IBaseMvpPresenter<View>{

    }
}