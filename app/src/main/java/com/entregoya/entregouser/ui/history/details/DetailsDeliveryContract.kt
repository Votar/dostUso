package com.entregoya.entregouser.ui.history.details

import com.entregoya.entregouser.entity.route.EntregoPointBinding
import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView
import com.google.android.gms.maps.GoogleMap

interface DetailsDeliveryContract {
    interface View : IBaseMvpView {
        fun showIncidentsActivity()
        fun setupWayoints(map: GoogleMap?, waypoints: Array<EntregoPointBinding>)
        fun drawRoute(map: GoogleMap?, path: String)
        fun loadMapAsync()
        fun moveCameraToRouteByBounds(map: GoogleMap?, waypoints: Array<EntregoPointBinding>)
    }

    interface Presenter : IBaseMvpPresenter<View>
}