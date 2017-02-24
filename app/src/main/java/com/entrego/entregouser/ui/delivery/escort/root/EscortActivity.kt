package com.entrego.entregouser.ui.delivery.escort.root

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.databinding.ActivityEscortBinding
import com.entrego.entregouser.entity.common.EntregoMessengerView
import com.entrego.entregouser.entity.delivery.EntregoDelivery
import com.entrego.entregouser.entity.route.EntregoPointBinding
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.delivery.escort.cancel.CancelDeliveryActivity
import com.entrego.entregouser.ui.delivery.escort.status.StatusDeliveryActivity
import com.entrego.entregouser.ui.delivery.finish.FinishDeliveryActivity
import com.entrego.entregouser.util.getCurrentPoint
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_escort.*
import kotlinx.android.synthetic.main.escort_description_layout.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import java.util.*

class EscortActivity : BaseMvpActivity<EscortContract.View, EscortContract.Presenter>(),
        EscortContract.View {


    companion object {
        val KEY_DELIVERY = "ext_k_delivery"
        fun getIntent(ctx: Context, delivery: EntregoDelivery): Intent {
            val intent = Intent(ctx, EscortActivity::class.java)
            val jsonDelivery = Gson().toJson(delivery, EntregoDelivery::class.java)
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
        val delivery = Gson().fromJson(jsonDelivery, EntregoDelivery::class.java)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_escort)
        binder.delivery = delivery
        setupLayouts()
    }

    override fun getRootView(): View = escort_sliding_layout

    override fun onStart() {
        super.onStart()
        mPresenter.loadMapAsync()
        binder?.delivery?.id?.let {
            mPresenter.requestDeliveryStatus(it)
        }
        startStatusTimer()
    }

    override fun onStop() {
        super.onStop()
        stopStatusTimer()
    }

    fun setupLayouts() {
        escort_cancel_fl.setOnClickListener {
            startActivity(Intent(this, CancelDeliveryActivity::class.java))
        }
        escort_call_messenger_fl.setOnClickListener { mPresenter.callMessenger() }
        escort_chat_messenger_fl.setOnClickListener { mPresenter.chatMessenger() }
        escort_status_fl.setOnClickListener { showStatusDelivery() }
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }

    }

    override fun setupMessengerView(messenger: EntregoMessengerView) {
        binder.messenger = messenger
        escort_messenger_form.visibility = View.VISIBLE
        escort_messenger_name.text = messenger.name
    }

    override fun setupWayoints(pointsArray: Array<EntregoPointBinding>) {
        setupNextPoint(pointsArray.getCurrentPoint())


    }

    override fun showFinishDelivery(deliveryId: Long, messenger: EntregoMessengerView) {
        startActivity(FinishDeliveryActivity.getIntent(this, deliveryId, messenger))
    }


    fun setupNextPoint(point: EntregoPointBinding) {
        escort_next_point_address.text = point.address
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
        binder.messenger?.let {
            startActivity(StatusDeliveryActivity.getIntent(this, binder.messenger, binder.delivery))
        }
    }
}
