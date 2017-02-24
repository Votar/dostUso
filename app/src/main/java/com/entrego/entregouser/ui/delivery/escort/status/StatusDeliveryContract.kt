package com.entrego.entregouser.ui.delivery.escort.status

import android.widget.ImageView
import com.entrego.entregouser.entity.route.EntregoRouteModel
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView

interface StatusDeliveryContract {
    interface View : IBaseMvpView{

    }
    interface Presenter: IBaseMvpPresenter<View>{
        fun buildSwitchListByState(route: EntregoRouteModel, switchList: List<ImageView>)
    }
}