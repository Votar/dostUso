package com.entrego.entregouser.ui.delivery.escort.root

import android.app.FragmentManager
import com.entrego.entregouser.entity.common.EntregoMessengerView
import com.entrego.entregouser.entity.route.EntregoPointBinding
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.google.android.gms.maps.model.LatLng


object EscortContract {
    interface View : IBaseMvpView {
        fun getSupportFragmentManager(): android.support.v4.app.FragmentManager
        fun setupMessengerView(messenger : EntregoMessengerView)
        fun setupWayoints(pointsArray: Array<EntregoPointBinding>)

    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun cancelDelivery()
        fun showStatusDelivery()
        fun callMessenger()
        fun chatMessenger()
        fun shareDelivery()
        fun loadMapAsync()
        fun moveCamera(position: LatLng)
        fun requestDeliveryStatus(deliveryId:Long)
        fun replaceMessengerMarker()

    }
}