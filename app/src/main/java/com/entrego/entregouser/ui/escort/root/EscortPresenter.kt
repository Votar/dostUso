package com.entrego.entregouser.ui.escort.root

import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class EscortPresenter : BaseMvpPresenter<EscortContract.View>(),
        EscortContract.Presenter, OnMapReadyCallback {

    //Dnipro
    val defaulLocation = LatLng(8.386368, -81.0629982)
    var mMap: GoogleMap? = null

    override fun attachView(view: EscortContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
        mMap = null
    }

    override fun cancelDelivery() {

    }

    override fun showStatusDelivery() {

    }

    override fun shareRouteBySocial() {

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
        moveCamera(defaulLocation)
    }

}