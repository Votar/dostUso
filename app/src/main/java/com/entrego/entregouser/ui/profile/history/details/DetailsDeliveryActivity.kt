package com.entrego.entregouser.ui.profile.history.details

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.databinding.ActivityDetailsDeliveryBinding
import com.entrego.entregouser.entity.back.EntregoDeliveryPreview
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.profile.history.incidents.IncidentsActivity
import com.entrego.entregouser.util.GsonHolder
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_details_delivery.*
import kotlinx.android.synthetic.main.history_details_description_layout.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class DetailsDeliveryActivity : BaseMvpActivity<DetailsDeliveryContract.View,
        DetailsDeliveryContract.Presenter>(),
        DetailsDeliveryContract.View {

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
    }

    override fun onStart() {
        super.onStart()
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
        startActivity(Intent(this, IncidentsActivity::class.java))
    }

    fun deserializeDelivery() {
        if (intent.hasExtra(KEY_DELIVERY)) {
            val delivery = GsonHolder
                    .instance
                    .fromJson(
                            intent.getStringExtra(KEY_DELIVERY), EntregoDeliveryPreview::class.java
                    )
            mBinder.delivery = delivery
            mBinder.messenger = delivery.order.messenger
        }
    }

}
