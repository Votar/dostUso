package com.entrego.entregouser.ui.delivery.escort.root

import com.entrego.entregouser.entity.back.EntregoWaypoint
import com.entrego.entregouser.entity.common.EntregoMessengerView
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.google.android.gms.maps.model.LatLng


object EscortContract {
    interface View : IBaseMvpView {
        fun getSupportFragmentManager(): android.support.v4.app.FragmentManager
        fun setupMessengerView(messenger: EntregoMessengerView)
        fun setupWayoints(waypoints: Array<EntregoWaypoint>)
        fun showFinishDelivery(deliveryId: Long, messenger: EntregoMessengerView)
        fun showStatusDelivery()

    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun cancelDelivery()
        fun callMessenger()
        fun chatMessenger()
        fun shareDelivery()
        fun loadMapAsync()
        fun moveCamera(position: LatLng)
        fun requestDeliveryStatus(deliveryId: Long)
        fun replaceMessengerMarker()

    }
}