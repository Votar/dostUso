package com.entrego.entregouser.ui.history.details

import com.entrego.entregouser.entity.route.EntregoPointBinding
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
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