package com.entrego.entregouser.ui.delivery.escort.root

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.databinding.ActivityEscortBinding
import com.entrego.entregouser.entity.back.EntregoDeliveryPreview
import com.entrego.entregouser.entity.back.EntregoWaypoint
import com.entrego.entregouser.entity.back.HistoryHolder
import com.entrego.entregouser.entity.common.EntregoMessengerView
import com.entrego.entregouser.entity.common.EntregoPriceEntity
import com.entrego.entregouser.entity.route.EntregoPointBinding
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.delivery.escort.cancel.CancelDeliveryActivity
import com.entrego.entregouser.ui.delivery.escort.status.StatusDeliveryActivity
import com.entrego.entregouser.ui.delivery.finish.FinishDeliveryActivity
import com.entrego.entregouser.util.GsonHolder
import com.entrego.entregouser.util.loadMessengerPicWithToken
import com.entrego.entregouser.util.logd
import com.entrego.entregouser.web.socket.SocketContract
import com.entrego.entregouser.web.socket.model.MessengerLocationMessage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.android.synthetic.main.activity_escort.*
import kotlinx.android.synthetic.main.escort_description_layout.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import java.util.*


class EscortActivity : BaseMvpActivity<EscortContract.View, EscortContract.Presenter>(),
        EscortContract.View {


    companion object {
        val KEY_DELIVERY = "ext_k_delivery"
        fun getIntent(ctx: Context, delivery: EntregoDeliveryPreview): Intent {
            val intent = Intent(ctx, EscortActivity::class.java)
            val jsonDelivery = GsonHolder.instance.toJson(delivery, EntregoDeliveryPreview::class.java)
            intent.putExtra(KEY_DELIVERY, jsonDelivery)
            return intent
        }
    }

    override var mPresenter: EscortContract.Presenter = EscortPresenter()
    var mTimer: Timer? = null
    lateinit var binder: ActivityEscortBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonDelivery = intent.getStringExtra(KEY_DELIVERY)
        val delivery = GsonHolder.instance.fromJson(jsonDelivery, EntregoDeliveryPreview::class.java)
        logd(delivery.toString())
        binder = DataBindingUtil.setContentView(this, R.layout.activity_escort)
        binder.delivery = delivery
        mPresenter.setupDelivery(delivery)
        mPresenter.loadMapAsync()
        setupLayouts()
        registerUpdateDeliveryReceiver()
        registerUpdateMessengerLocationReceiver()
        registerUpdateOrderReceiver()

    }

    override fun getRootView(): View = escort_sliding_layout

    override fun onStart() {
        super.onStart()
        binder.delivery?.id?.let {
            mPresenter.requestOrderStatus(it)
        }


    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterUpdateDeliveryReceiver()
        unregisterUpdateMessengerLocationReceiver()
        unregisterUpdateOrderReceiver()
    }

    fun setupLayouts() {
        escort_cancel_fl.setOnClickListener {
            startActivity(CancelDeliveryActivity.getIntent(this, binder.delivery.id))
        }
        escort_share_fl.setOnClickListener { mPresenter.shareDelivery() }
        escort_call_messenger_fl.setOnClickListener { mPresenter.callMessenger() }
        escort_chat_messenger_fl.setOnClickListener { mPresenter.chatMessenger() }
        escort_status_fl.setOnClickListener { showStatusDelivery() }
        nav_toolbar_back.setOnClickListener { onBackPressed() }
    }


    override fun setupMessengerView(messenger: EntregoMessengerView?) {
        binder.messenger = messenger
        escort_messenger_form.visibility = View.VISIBLE
        escort_messenger_name.text = messenger?.name
        if (messenger?.id != null)
            escort_messenger_pic.loadMessengerPicWithToken(messenger.id)
        binder.invalidateAll()
    }


    override fun showFinishDelivery(deliveryId: Long,
                                    orderId: Long,
                                    price: EntregoPriceEntity,
                                    messenger: EntregoMessengerView) {
        startActivity(FinishDeliveryActivity.getIntent(this, deliveryId, orderId, price, messenger))
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(orderId.toInt())
        finish()
    }


    override fun setupNextPoint(waypointAddress: String) {
        escort_next_point_address.text = waypointAddress
    }

    override fun showStatusDelivery() {
        if (binder.messenger == null || binder.waypoints == null)
            showMessage(R.string.error_no_messenger_yet)
        else
            startActivity(StatusDeliveryActivity.getIntent(this, binder.messenger,
                    binder.delivery,
                    binder.waypoints.value))

    }

    fun registerUpdateDeliveryReceiver() {
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(
                        mUpdateDeliveryEventReceiver,
                        IntentFilter(SocketContract.UpdateDeliveryEvent.ACTION)
                )
    }

    fun unregisterUpdateDeliveryReceiver() {
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mUpdateDeliveryEventReceiver)
    }

    fun registerUpdateMessengerLocationReceiver() {
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(
                        mUpdateMessengerLocationReceiver,
                        IntentFilter(SocketContract.UpdateMessengerLocationEvent.ACTION)
                )
    }

    fun unregisterUpdateMessengerLocationReceiver() {
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mUpdateMessengerLocationReceiver)
    }

    fun registerUpdateOrderReceiver() {
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(
                        mUpdateOrderReceiver,
                        IntentFilter(SocketContract.UpdateOrderEvent.ACTION)
                )

    }

    fun unregisterUpdateOrderReceiver() {
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mUpdateOrderReceiver)
    }

    override fun moveCameraToRouteByBounds(map: GoogleMap, waypoints: Array<EntregoPointBinding>) {
        val boundsBuilder = LatLngBounds.Builder()
        waypoints.forEach { boundsBuilder.include(it.point) }
        val bounds = boundsBuilder.build()

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels - escort_navbar.height
        val padding = (width * 0.12).toInt() // offset from edges of the map 12% of screen
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding))

    }

    override fun getRoutePath(): String = binder.delivery.route.path.line


    val mUpdateDeliveryEventReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.hasExtra(SocketContract.UpdateDeliveryEvent.KEY_DELIVERY_ID) == true) {
                val deliveryId = intent.getLongExtra(SocketContract.UpdateDeliveryEvent.KEY_DELIVERY_ID, 0)
                binder.delivery?.apply {
                    if (deliveryId == this.id)
                        mPresenter.requestDeliveryStatus(id)
                }
            } else throw IllegalStateException("No delivery id in intent with UpdateDeliveryEvent")
        }
    }

    val mUpdateMessengerLocationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.hasExtra(SocketContract
                    .UpdateMessengerLocationEvent
                    .KEY_MESSENGER_LOCATION) == true) {
                val messengerLocationJson = intent.getStringExtra(
                        SocketContract
                                .UpdateMessengerLocationEvent
                                .KEY_MESSENGER_LOCATION
                )
                val messageModel = GsonHolder.instance
                        .fromJson(messengerLocationJson, MessengerLocationMessage::class.java)

                mPresenter.replaceMessengerMarker(messageModel.order, messageModel.coordinates)

            } else throw IllegalStateException("No messenger location in intent with UpdateMessengerLocationEvent")
        }
    }

    val mUpdateOrderReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.hasExtra(SocketContract
                    .UpdateOrderEvent
                    .KEY_DELIVERY_ID) == true) {
                val deliveryId = intent.getLongExtra(SocketContract.UpdateDeliveryEvent.KEY_DELIVERY_ID, 0)
                binder.delivery?.apply {
                    if (deliveryId == this.id)
                        mPresenter.requestOrderStatus(id)
                }
            } else throw IllegalStateException("No delivery id in intent with UpdateOrderEvent")
        }

    }


    override fun setupStatusDelivery(waypoints: Array<EntregoWaypoint>) {
        binder.waypoints = HistoryHolder(waypoints)
    }


}
