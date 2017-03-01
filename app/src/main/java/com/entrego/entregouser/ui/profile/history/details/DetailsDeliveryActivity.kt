package com.entrego.entregouser.ui.profile.history.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.profile.history.incidents.IncidentsActivity
import com.entrego.entregouser.util.logd
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_details_delivery.*
import kotlinx.android.synthetic.main.history_details_description_layout.*

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
    }

    override fun showIncidentsActivity() {
        startActivity(Intent(this, IncidentsActivity::class.java))
    }

}
