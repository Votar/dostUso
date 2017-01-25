package com.entrego.entregouser.ui.main.mvp.view

import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener


interface IRootView {
    fun onBuildMap()
    fun requestPermissions(listener: PermissionListener)
    fun moveCamera(position: LatLng, smooth: Boolean = false)

}