package com.entrego.entregouser.ui.profile.history.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.profile.history.incidents.IncidentsActivity
import com.entrego.entregouser.util.logd
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_details_delivery.*
import kotlinx.android.synthetic.main.history_details_description_layout.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class DetailsDeliveryActivity : BaseMvpActivity<DetailsDeliveryContract.View, DetailsDeliveryContract.Presenter>(),
        DetailsDeliveryContract.View {



    override var mPresenter: DetailsDeliveryContract.Presenter = DetailsDeliveryPresenter()

    override fun getRootView(): View? = activity_details_delivery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_delivery)
        setupLayouts()
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
            SlidingUpPanelLayout.PanelState.COLLAPSED -> activity_details_delivery.panelState = SlidingUpPanelLayout.PanelState.EXPANDED

            SlidingUpPanelLayout.PanelState.EXPANDED -> activity_details_delivery.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }

    fun setupListeners() {
        history_details_bill_btn.setOnClickListener { toggleSwipePanel() }
        details_delivery_incidents_btn.setOnClickListener { showIncidentsActivity() }
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
    }

    override fun showIncidentsActivity() {
        startActivity(Intent(this, IncidentsActivity::class.java))
    }

}
