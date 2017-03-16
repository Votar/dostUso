package com.entrego.entregouser.ui.delivery.escort.root

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.EntregoPhoneModel
import com.entrego.entregouser.entity.back.*
import com.entrego.entregouser.entity.delivery.EntregoDeliveryStatuses
import com.entrego.entregouser.entity.route.EntregoPointBinding
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.ui.delivery.escort.chat.ChatMessengerActivity
import com.entrego.entregouser.ui.delivery.escort.root.model.GetDeliveryRequest
import com.entrego.entregouser.ui.delivery.escort.root.model.GetDeliveryStatusRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.util.*


class EscortPresenter : BaseMvpPresenter<EscortContract.View>(),
        EscortContract.Presenter, OnMapReadyCallback {


    val mToken: String = PreferencesManager.getTokenOrEmpty()
    //Dnipro
    val mDefaultLocation = LatLng(8.386368, -81.0629982)
    var mMessengerPhone: EntregoPhoneModel? = null
    var mMap: GoogleMap? = null
    var isViewPrepared = false
    lateinit var mDelivery: EntregoDeliveryPreview
    val mGetDeliveryStatusListener = object : GetDeliveryStatusRequest.ResponseListener {
        override fun onSuccessResponse(result: EntregoOrderView) {
            mView?.setupNextPoint(result.waypoints.getCurrentPoint().waypoint.address)
            mView?.setupMessengerView(result.messenger)
            mView?.setupStatusDelivery(result.waypoints)
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
        if (mMessengerPhone == null)
            mView?.showError(R.string.error_no_messenger_yet)
        else {
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

        if (mMessengerPhone == null) {
            mView?.showError(R.string.error_no_messenger_yet)
            return
        }
        val ctx = mView?.getAppContext()
        val orderId = mDelivery.order.id
        val userID = EntregoStorage.getProfile()?.apply {
            ctx?.let { it.startActivity(ChatMessengerActivity.getIntent(it, orderId, id)) }
        }
    }

    override fun cancelDelivery() {

    }


    override fun shareDelivery() {

    }

    override fun loadMapAsync() {
        mView?.let {
            val mapFragment = it.getSupportFragmentManager()
                    .findFragmentById(R.id.escort_map) as? SupportMapFragment
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
        setupView()
    }

    val mGetDeliveryResponseLisnter = object : GetDeliveryRequest.ResponseListener {
        override fun onFailureResponse(code: Int?, message: String?) {

        }

        override fun onSuccessResponse(result: EntregoDeliveryPreview) {
            mDelivery = result
            when (result.status) {
                EntregoDeliveryStatuses.PENDING -> {
                }
                EntregoDeliveryStatuses.ASSIGNED -> requestDeliveryStatus(result.id)
                EntregoDeliveryStatuses.DELIVERED -> {
                    mView?.showFinishDelivery(result.id, result.price, result.order.messenger)
                }
                EntregoDeliveryStatuses.CANCELED -> {
                    mView?.showMessage(R.string.message_delivery_have_canceled)
                    mView?.setupMessengerView(null)
                    mMessengerPhone = null
                }
                EntregoDeliveryStatuses.CONFIRMATION -> {
                }
            }
        }
    }

    override fun requestOrderStatus(deliveryId: Long) {
        val token = EntregoStorage.getTokenOrEmpty()
        GetDeliveryRequest().requestAsync(token, deliveryId, mGetDeliveryResponseLisnter)
    }


    fun setupView() {
        mMap?.let {
            drawRoute(mDelivery.route.path.line)
            mView?.moveCameraToRouteByBounds(it, mDelivery.route.waypoints)
            setupWayoints(mDelivery.route.waypoints)
        }
    }


    override fun setupDelivery(delivery: EntregoDeliveryPreview) {
        mDelivery = delivery
        setupView()
    }

    override fun drawRoute(path: String) {
        val points = PolyUtil.decode(path)
        val polylineOptions = PolylineOptions()
                .geodesic(true)
                .color(ContextCompat.getColor(mView?.getAppContext(), R.color.colorDarkBlue))
                .width(10f)
        for (i in 0..points.size - 1)
            polylineOptions.add(points[i])
        mMap?.addPolyline(polylineOptions)

    }

    override fun setupWayoints(waypoints: Array<EntregoPointBinding>) {
        //add start point
        val startLatLng = waypoints.getCurrentPoint().point
        mView?.getAppContext()
        mMap?.addMarker(MarkerOptions()
                .position(startLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))

        //add finish point
        val finishLatLng = waypoints.getDestinationPoint().point
        mMap?.addMarker(MarkerOptions()
                .position(finishLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))

        waypoints.getOtherPoints().forEach {
            mMap?.addMarker(
                    MarkerOptions()
                            .position(it.point)
                            .draggable(false)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
        }
    }

}