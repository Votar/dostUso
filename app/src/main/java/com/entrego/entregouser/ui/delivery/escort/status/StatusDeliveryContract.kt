package com.entrego.entregouser.ui.delivery.escort.status

import android.widget.ImageView
import com.entrego.entregouser.entity.back.EntregoWaypoint
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView

interface StatusDeliveryContract {
    interface View : IBaseMvpView
    interface Presenter: IBaseMvpPresenter<View>{
        fun buildSwitchListByState(waypoints: Array<EntregoWaypoint>, switchList: List<ImageView>)
    }
}