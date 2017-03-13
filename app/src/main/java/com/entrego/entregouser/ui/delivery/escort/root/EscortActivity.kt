package com.entrego.entregouser.ui.delivery.escort.root

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
import com.entrego.entregouser.entity.back.getCurrentPoint
import com.entrego.entregouser.entity.common.EntregoMessengerView
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.delivery.escort.cancel.CancelDeliveryActivity
import com.entrego.entregouser.ui.delivery.escort.status.StatusDeliveryActivity
import com.entrego.entregouser.ui.delivery.finish.FinishDeliveryActivity
import com.entrego.entregouser.util.GsonHolder
import com.entrego.entregouser.web.socket.SocketContract
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
        binder = DataBindingUtil.setContentView(this, R.layout.activity_escort)
        binder.delivery = delivery
        setupLayouts()
    }

    override fun getRootView(): View = escort_sliding_layout

    override fun onStart() {
        super.onStart()
        mPresenter.loadMapAsync()
        binder.delivery?.id?.let {
            mPresenter.requestDeliveryStatus(it)
        }

        registerUpdateDeliveryReceiver()
        registerUpdateMessengerLocationReceiver()
//        startStatusTimer()

    }

    override fun onStop() {
        super.onStop()
        unregisterUpdateDeliveryReceiver()
        unregisterUpdateMessengerLocationReceiver()
//        stopStatusTimer()
    }

    fun setupLayouts() {
        escort_cancel_fl.setOnClickListener {
            startActivity(Intent(this, CancelDeliveryActivity::class.java))
        }
        escort_call_messenger_fl.setOnClickListener { mPresenter.callMessenger() }
        escort_chat_messenger_fl.setOnClickListener { mPresenter.chatMessenger() }
        escort_status_fl.setOnClickListener { showStatusDelivery() }
        nav_toolbar_back.setOnClickListener { onBackPressed() }

    }

    override fun setupMessengerView(messenger: EntregoMessengerView) {
        binder.messenger = messenger
        escort_messenger_form.visibility = View.VISIBLE
        escort_messenger_name.text = messenger.name
    }

    override fun setupWayoints(waypoints: Array<EntregoWaypoint>) {
        setupNextPoint(waypoints.getCurrentPoint())
    }

    override fun showFinishDelivery(deliveryId: Long, messenger: EntregoMessengerView) {
        startActivity(FinishDeliveryActivity.getIntent(this, deliveryId, messenger))
    }


    fun setupNextPoint(waypoint: EntregoWaypoint) {
        escort_next_point_address.text = waypoint.waypoint.address
    }

    fun startStatusTimer() {
        stopStatusTimer()
        mTimer = Timer()
        mTimer?.schedule(object : TimerTask() {
            override fun run() {
                binder.delivery?.apply { mPresenter.requestDeliveryStatus(id) }
            }
        }, 1000, 3000)
    }

    fun stopStatusTimer() {
        mTimer?.purge()
        mTimer?.cancel()
        mTimer = null
    }

    override fun showStatusDelivery() {
        if (binder.messenger == null)
            showMessage(R.string.error_no_messenger_yet)
        else
            startActivity(StatusDeliveryActivity.getIntent(this, binder.messenger, binder.delivery))

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


    val mUpdateDeliveryEventReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.hasExtra(SocketContract.UpdateDeliveryEvent.KEY_DELIVERY_ID) == true) {
                val deliveryId = intent.getLongExtra(SocketContract.UpdateDeliveryEvent.KEY_DELIVERY_ID, 0)
                binder.delivery?.apply {
//                    if (deliveryId == this.id)
                        mPresenter.requestDeliveryStatus(isd)
                }
            } else throw Exception("No delivery id in intent with UpdateDeliveryEvent")
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
            } else throw Exception("No messenger location in intent with UpdateMessengerLocationEvent")
        }
    }
}
