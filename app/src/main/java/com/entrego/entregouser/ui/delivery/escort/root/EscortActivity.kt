package com.entrego.entregouser.ui.delivery.escort.root

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.delivery.escort.cancel.CancelDeliveryActivity
import kotlinx.android.synthetic.main.activity_escort.*
import kotlinx.android.synthetic.main.escort_description_layout.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class EscortActivity : BaseMvpActivity<EscortContract.View, EscortContract.Presenter>(),
        EscortContract.View {

    override var mPresenter: EscortContract.Presenter = EscortPresenter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escort)
        setupLayouts()
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
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
    }




}
