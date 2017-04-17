package com.entregoya.entregouser.ui.delivery.escort.status

import android.widget.ImageView
import com.entregoya.entregouser.entity.back.EntregoWaypoint
import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView

interface StatusDeliveryContract {
    interface View : IBaseMvpView
    interface Presenter: IBaseMvpPresenter<View>{
        fun buildSwitchListByState(waypoints: Array<EntregoWaypoint>, switchList: List<ImageView>)
    }
}