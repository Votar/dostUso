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
import com.google.android.gms.maps.model.*
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
    var mDelivery: EntregoDeliveryPreview? = null
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

    override fun detachView() {
        super.detachView()
        mMap = null
    }

    override fun requestDeliveryStatus(deliveryId: Long) {
        GetDeliveryStatusRequest().requestAsync(mToken, deliveryId, mGetDeliveryStatusListener)
    }

    var mMessengerMarker: Marker? = null
    override fun replaceMessengerMarker(orderId: Long, coordinates: LatLng) {
        if (mDelivery?.order?.id != null)
            if (mMessengerMarker == null)
                mMessengerMarker = mMap?.addMarker(MarkerOptions()
                        .position(coordinates)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_user_pin))
                        .draggable(false))
            else
                mMessengerMarker?.position = coordinates
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
        val orderId = mDelivery?.order?.id
        val userID = EntregoStorage.getProfile()?.apply {
            if (orderId != null)
                ctx?.let { it.startActivity(ChatMessengerActivity.getIntent(it, orderId, id)) }
            else throw IllegalStateException("Order id in mDelivery is null")
        }
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
                    if (result.order != null)
                        mView?.showFinishDelivery(
                                result.id,
                                result.order.id,
                                result.price,
                                result.order.messenger
                        )
                    else
                        throw IllegalStateException("Order from getDelivery is null")
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
        mDelivery?.apply {
            drawRoute(route.path.line)
            mMap?.also { mView?.moveCameraToRouteByBounds(it, route.waypoints) }
            setupWayoints(route.waypoints)
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