package com.entregoya.entregouser.ui.history.details

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.entregoya.entregouser.R
import com.entregoya.entregouser.databinding.ActivityDetailsDeliveryBinding
import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.entity.back.getCurrentPoint
import com.entregoya.entregouser.entity.back.getDestinationPoint
import com.entregoya.entregouser.entity.back.getOtherPoints
import com.entregoya.entregouser.entity.route.EntregoPointBinding
import com.entregoya.entregouser.mvp.view.BaseMvpActivity
import com.entregoya.entregouser.ui.history.incidents.IncidentsActivity
import com.entregoya.entregouser.util.GsonHolder
import com.entregoya.entregouser.util.loadMessengerPicWithToken
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_details_delivery.*
import kotlinx.android.synthetic.main.history_details_description_layout.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class DetailsDeliveryActivity : BaseMvpActivity<DetailsDeliveryContract.View,
        DetailsDeliveryContract.Presenter>(),
        DetailsDeliveryContract.View, OnMapReadyCallback {


    companion object {
        private const val KEY_DELIVERY = "ext_k_delivery"
        fun getIntent(ctx: Context, delivery: EntregoDeliveryPreview): Intent {
            val intent = Intent(ctx, DetailsDeliveryActivity::class.java)
            intent.putExtra(KEY_DELIVERY,
                    GsonHolder
                            .instance
                            .toJson(delivery, EntregoDeliveryPreview::class.java)
            )
            return intent
        }
    }


    override var mPresenter: DetailsDeliveryContract.Presenter = DetailsDeliveryPresenter()

    override fun getRootView(): View? = activity_details_delivery
    lateinit var mBinder: ActivityDetailsDeliveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_details_delivery)
        setupLayouts()
        deserializeDelivery()
        loadMapAsync()
    }

    fun setupLayouts() {
        activity_details_delivery.isTouchEnabled = false
        setupListeners()
    }

    private fun toggleSwipePanel() {

        when (activity_details_delivery.panelState) {
            SlidingUpPanelLayout.PanelState.COLLAPSED ->
                activity_details_delivery.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            SlidingUpPanelLayout.PanelState.EXPANDED ->
                activity_details_delivery.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }

    fun setupListeners() {
        history_details_bill_btn.setOnClickListener { toggleSwipePanel() }
        details_delivery_incidents_btn.setOnClickListener { showIncidentsActivity() }
        nav_toolbar_back.setOnClickListener { onBackPressed() }
    }

    override fun showIncidentsActivity() {
        startActivity(IncidentsActivity.getIntent(this, intent.getStringExtra(KEY_DELIVERY)))
    }

    fun deserializeDelivery() {
        if (intent.hasExtra(KEY_DELIVERY)) {
            val delivery = GsonHolder
                    .instance
                    .fromJson(
                            intent.getStringExtra(KEY_DELIVERY), EntregoDeliveryPreview::class.java
                    )
            mBinder.delivery = delivery
            mBinder.messenger = delivery?.order?.messenger
            history_details_msngr_photo.loadMessengerPicWithToken(mBinder.messenger.id)
        } else throw IllegalStateException("No delivery in intent")
    }

    override fun drawRoute(map: GoogleMap?, path: String) {
        val points = PolyUtil.decode(path)
        val polylineOptions = PolylineOptions()
                .geodesic(true)
                .color(ContextCompat.getColor(this, R.color.colorDarkBlue))
                .width(10f)
        for (i in 0..points.size - 1)
            polylineOptions.add(points[i])
        map?.addPolyline(polylineOptions)

    }

    override fun setupWayoints(map: GoogleMap?, waypoints: Array<EntregoPointBinding>) {

        //add start point
        val startLatLng = waypoints.getCurrentPoint().point

        map?.addMarker(MarkerOptions()
                .position(startLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))

        //add finish point
        val finishLatLng = waypoints.getDestinationPoint().point
        map?.addMarker(MarkerOptions()
                .position(finishLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))

        waypoints.getOtherPoints().forEach {
            map?.addMarker(
                    MarkerOptions()
                            .position(it.point)
                            .draggable(false)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        drawRoute(map, mBinder.delivery.route.path.line)
        setupWayoints(map, mBinder.delivery.route.waypoints)
        moveCameraToRouteByBounds(map, mBinder.delivery.route.waypoints)
    }

    override fun loadMapAsync() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.details_delivery_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun moveCameraToRouteByBounds(map: GoogleMap?, waypoints: Array<EntregoPointBinding>) {
        val boundsBuilder = LatLngBounds.Builder()
        waypoints.forEach { boundsBuilder.include(it.point) }
        val bounds = boundsBuilder.build()

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels - details_delivery_navbar.height
        val padding = (width * 0.12).toInt() // offset from edges of the map 12% of screen
        map?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding))
    }


}
