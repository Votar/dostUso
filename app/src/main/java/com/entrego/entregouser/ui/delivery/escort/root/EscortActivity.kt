package com.entrego.entregouser.ui.delivery.escort.root

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.databinding.ActivityEscortBinding
import com.entrego.entregouser.entity.delivery.EntregoDelivery
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.delivery.escort.cancel.CancelDeliveryActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_escort.*
import kotlinx.android.synthetic.main.escort_description_layout.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonDelivery = intent.getStringExtra(KEY_DELIVERY)
        val delivery = Gson().fromJson(jsonDelivery, EntregoDelivery::class.java)
        val binder: ActivityEscortBinding = DataBindingUtil.setContentView(this, R.layout.activity_escort)
        binder.delivery = delivery
        setupLayouts()
    }

    override fun getRootView(): View = escort_sliding_layout

    override fun onStart() {
        super.onStart()
        mPresenter.loadMapAsync()
    }

    fun setupLayouts() {
        escort_cancel_fl.setOnClickListener {
            startActivity(Intent(this, CancelDeliveryActivity::class.java))
        }
        escort_call_messenger_fl.setOnClickListener { mPresenter.callMessenger() }
        escort_chat_messenger_fl.setOnClickListener { mPresenter.chatMessenger() }

        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }

    }


}
