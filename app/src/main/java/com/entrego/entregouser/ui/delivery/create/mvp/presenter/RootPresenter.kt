package com.entrego.entregouser.ui.delivery.create.mvp.presenter

import android.app.Activity
import android.content.Intent
import com.entrego.entregouser.ui.delivery.create.mvp.view.IRootView
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import java.util.*

class RootPresenter : IRootPresenter {
    override fun shareLinkOnSite(activity: Activity) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.whatsapp.com/?l=ru")
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this site!");
        activity.startActivity(sendIntent)
    }

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

    override fun onCreate(view: IRootView) {
        mView = view
        mView?.requestPermissions(mPermissionListener)
    }

    override fun onStart() {
    }

    override fun onMapReady() {
        mView?.moveCamera(mDefaultLocation, false)
        mView?.showWelcomeBuilder()
    }

    override fun onStop() {
        mView = null
    }
}
