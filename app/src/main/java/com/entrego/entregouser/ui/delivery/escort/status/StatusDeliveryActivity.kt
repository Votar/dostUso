package com.entrego.entregouser.ui.delivery.escort.status

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.entrego.entregouser.R
import com.entrego.entregouser.databinding.ActivityTrackStatusDeliveryBinding
import com.entrego.entregouser.entity.back.EntregoDeliveryPreview
import com.entrego.entregouser.entity.back.EntregoWaypoint
import com.entrego.entregouser.entity.common.EntregoMessengerView
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.util.GsonHolder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_track_status_delivery.*
import kotlinx.android.synthetic.main.include_delivery_route.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import kotlinx.android.synthetic.main.status_service_delivered.*
import kotlinx.android.synthetic.main.status_service_on_the_way.*
import kotlinx.android.synthetic.main.status_service_ship_on_way.*
import kotlinx.android.synthetic.main.status_service_waiting_for_delivery.*
import kotlinx.android.synthetic.main.status_service_waiting_for_ship.*
import java.lang.reflect.Type

class StatusDeliveryActivity : BaseMvpActivity<StatusDeliveryContract.View,
        StatusDeliveryContract.Presenter>(),
        StatusDeliveryContract.View {

    companion object {
        private const val KEY_MESSENGER = "ext_k_messenger"
        private const val KEY_DELIVERY = "ext_k_delivery"
        private const val KEY_WAYPOINTS = "ext_k_waypoints"

        fun getIntent(ctx: Context, messenger: EntregoMessengerView,
                      delivery: EntregoDeliveryPreview,
                      waypoints: Array<EntregoWaypoint>): Intent {
            val intent = Intent(ctx, StatusDeliveryActivity::class.java)
            val gson = GsonHolder.instance
            intent.apply {
                putExtra(KEY_MESSENGER, gson.toJson(messenger, EntregoMessengerView::class.java))
                putExtra(KEY_DELIVERY, gson.toJson(delivery, EntregoDeliveryPreview::class.java))
                putExtra(KEY_WAYPOINTS, gson.toJson(waypoints, getArrayType()))
            }

            return intent
        }

        fun getArrayType(): Type = object : TypeToken<Array<EntregoWaypoint>>() {}.type
    }

    override fun getRootView(): View? = activity_track_status_delivery

    override var mPresenter: StatusDeliveryContract.Presenter = StatusDeliveryPresenter()
    lateinit var binder: ActivityTrackStatusDeliveryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil
                .setContentView<ActivityTrackStatusDeliveryBinding>(
                        this, R.layout.activity_track_status_delivery
                )
        val jsonMessenger = intent.getStringExtra(KEY_MESSENGER)
        val jsonDelivery = intent.getStringExtra(KEY_DELIVERY)
        val jsonWaypoints = intent.getStringExtra(KEY_WAYPOINTS)
        val gson = GsonHolder.instance
        binder.delivery = gson.fromJson(jsonDelivery, EntregoDeliveryPreview::class.java)
        binder.messenger = gson.fromJson(jsonMessenger, EntregoMessengerView::class.java)
        val waypoints = gson.fromJson<Array<EntregoWaypoint>>(jsonWaypoints, getArrayType())
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        mPresenter.buildSwitchListByState(waypoints, getSwitchList())
        setupAddressList(waypoints)
    }

    fun setupAddressList(waypoints: Array<EntregoWaypoint>) {
        status_address_recycler
                .layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        status_address_recycler.adapter = AddressListAdapter(waypoints.toList())
    }

    fun getSwitchList(): List<ImageView> =
            listOf(delivery_states_on_way_sw,
                    delivery_states_waiting_for_ship_sw,
                    delivery_states_ship_on_way_sw,
                    delivery_states_waiting_for_delivery_sw,
                    delivery_states_delivered_sw)
}
