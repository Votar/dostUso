package com.entrego.entregouser.ui.delivery.escort.root

import android.Manifest
import android.content.Intent
import android.net.Uri
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.EntregoPhoneModel
import com.entrego.entregouser.entity.common.EntregoStatusModel
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.ui.delivery.escort.chat.ChatMessengerActivity
import com.entrego.entregouser.ui.delivery.escort.root.model.GetDeliveryStatusRequest
import com.entrego.entregouser.ui.delivery.escort.status.StatusDeliveryActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.sql.Time
import java.util.*


class EscortPresenter : BaseMvpPresenter<EscortContract.View>(),
        EscortContract.Presenter, OnMapReadyCallback {

    val mToken: String = PreferencesManager.getTokenOrEmpty()
    //Dnipro
    val mDefaultLocation = LatLng(8.386368, -81.0629982)
    var mMessengerPhone: EntregoPhoneModel? = null
    var mMap: GoogleMap? = null
    val mGetDeliveryStatusListener = object : GetDeliveryStatusRequest.ResponseListener {
        override fun onSuccessResponse(result: EntregoStatusModel) {
            mView?.setupWayoints(result.waypoints)
            mView?.setupMessengerView(result.messenger)
            mMessengerPhone = result.messenger.phone
        }

        override fun onFailureResponse(code: Int?, message: String?) {
            when (code) {
                2 -> mView?.onLogout()
                9 -> {
                }
                else -> mView?.showError(message)
            }
        }
    }

    val mCallPermissionListener = object : PermissionListener {


        override fun onPermissionGranted() {
            callMessenger()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            mView?.showError(R.string.error_no_call_permissions)
        }
    }

    override fun attachView(view: EscortContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
        mMap = null
    }

    override fun requestDeliveryStatus(deliveryId: Long) {
        GetDeliveryStatusRequest().requestAsync(mToken, deliveryId, mGetDeliveryStatusListener)
    }

    override fun replaceMessengerMarker() {

    }

    override fun callMessenger() {
        if (mMessengerPhone == null) {
            mView?.showError(R.string.error_no_messenger_yet)
        } else {
            mView?.getAppContext()?.let {

                try {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:" + mMessengerPhone?.toView())
                    mView?.getAppContext()
                            ?.startActivity(intent)
                } catch (ex: SecurityException) {
                    TedPermission(it)
                            .setPermissionListener(mCallPermissionListener)
                            .setPermissions(Manifest.permission.CALL_PHONE)
                            .check()
                }
            }
        }
    }

    override fun chatMessenger() {
        val ctx = mView?.getAppContext()
        ctx?.let { it.startActivity(ChatMessengerActivity.getIntent(it)) }
    }

    override fun cancelDelivery() {

    }



    override fun shareDelivery() {

    }

    override fun loadMapAsync() {
        mView?.let {
            val mapFragment = it.getSupportFragmentManager()
                    .findFragmentById(R.id.map) as? SupportMapFragment
            mapFragment?.getMapAsync(EscortPresenter@ this)
        }
    }

    override fun moveCamera(position: LatLng) {
        val nextCamera = CameraUpdateFactory
                .newLatLngZoom(position, 16f)
        mMap?.moveCamera(nextCamera)
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map
        moveCamera(mDefaultLocation)
    }




}