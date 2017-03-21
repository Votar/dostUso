package com.entrego.entregouser.ui.delivery.escort.root

import com.entrego.entregouser.entity.back.EntregoDeliveryPreview
import com.entrego.entregouser.entity.back.EntregoWaypoint
import com.entrego.entregouser.entity.common.EntregoMessengerView
import com.entrego.entregouser.entity.common.EntregoPriceEntity
import com.entrego.entregouser.entity.route.EntregoPointBinding
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng


object EscortContract {
    interface View : IBaseMvpView {
        fun getSupportFragmentManager(): android.support.v4.app.FragmentManager
        fun setupMessengerView(messenger: EntregoMessengerView?)
        fun setupNextPoint(waypointAddress: String)
        fun showFinishDelivery(deliveryId: Long, orderId: Long, price: EntregoPriceEntity, messenger: EntregoMessengerView)
        fun setupStatusDelivery(waypoints: Array<EntregoWaypoint>)
        fun showStatusDelivery()
        fun getRoutePath(): String
        fun moveCameraToRouteByBounds(map: GoogleMap, waypoints: Array<EntregoPointBinding>)
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun callMessenger()
        fun chatMessenger()
        fun shareDelivery()

        fun moveCamera(position: LatLng)
        fun requestDeliveryStatus(deliveryId: Long)
        fun replaceMessengerMarker(orderId: Long, coordinates: LatLng)
        fun setupWayoints(waypoints: Array<EntregoPointBinding>)
        fun drawRoute(path: String)
        fun loadMapAsync()
        fun setupDelivery(delivery: EntregoDeliveryPreview)
        fun requestOrderStatus(deliveryId: Long)
    }
}