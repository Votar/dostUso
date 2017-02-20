package com.entrego.entregouser.ui.delivery.create.mvp.view

import android.app.Fragment
import com.entrego.entregouser.ui.delivery.create.mvp.model.FragmentType
import com.entrego.entregouser.web.model.response.delivery.create.EntregoDeliveryCreationResponse
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener


interface IRootView {
    fun onBuildMap()
    fun requestPermissions(listener: PermissionListener)
    fun moveCamera(position: LatLng, smooth: Boolean = false)
    fun showTypeShipmentFragment()
    fun showTypeTransactionFragment()
    fun showTypeDeliverFragment()
    fun showMessage(stringId: Int)
    fun showWelcomeBuilder()
    fun showPaymentMethods()

}