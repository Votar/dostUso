package com.entrego.entregouser.ui.main.mvp.presenter

import com.entrego.entregouser.ui.main.mvp.view.IRootView
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import java.util.*

class RootPresenter : IRootPresenter {

    var mView: IRootView? = null
    //Panama city
    val mDefaultLocation = LatLng(9.047261, -79.484000)
    val mPermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            mView?.onBuildMap()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
//
        }
    }

    override fun onStart(view: IRootView) {
        mView = view
        mView?.requestPermissions(mPermissionListener)
    }

    override fun onMapReady() {
        mView?.moveCamera(mDefaultLocation, false)
    }

    override fun onStop() {
        mView = null
    }
}
