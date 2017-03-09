package com.entrego.entregouser.ui.delivery.escort.status

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import android.widget.ImageView
import com.entrego.entregouser.R
import com.entrego.entregouser.databinding.ActivityTrackStatusDeliveryBinding
import com.entrego.entregouser.entity.back.EntregoDeliveryPreview
import com.entrego.entregouser.entity.common.EntregoMessengerView
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.util.GsonHolder
import kotlinx.android.synthetic.main.activity_track_status_delivery.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import kotlinx.android.synthetic.main.status_service_delivered.*
import kotlinx.android.synthetic.main.status_service_on_the_way.*
import kotlinx.android.synthetic.main.status_service_ship_on_way.*
import kotlinx.android.synthetic.main.status_service_waiting_for_delivery.*
import kotlinx.android.synthetic.main.status_service_waiting_for_ship.*

class StatusDeliveryActivity : BaseMvpActivity<StatusDeliveryContract.View,
        StatusDeliveryContract.Presenter>(),
        StatusDeliveryContract.View {

    companion object {
        private const val KEY_MESSENGER = "ext_k_messenger"
        private const val KEY_DELIVERY = "ext_k_delivery"

        fun getIntent(ctx: Context, messenger: EntregoMessengerView, delivery: EntregoDeliveryPreview): Intent {
            val intent = Intent(ctx, StatusDeliveryActivity::class.java)
            val gson = GsonHolder.instance
            intent.apply {
                putExtra(KEY_MESSENGER, gson.toJson(messenger, EntregoMessengerView::class.java))
                putExtra(KEY_DELIVERY, gson.toJson(delivery, EntregoDeliveryPreview::class.java))
            }

            return intent
        }
    }

    override fun getRootView(): View? = activity_track_status_delivery

    override var mPresenter: StatusDeliveryContract.Presenter = StatusDeliveryPresenter()
    lateinit var binder: ActivityTrackStatusDeliveryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView<ActivityTrackStatusDeliveryBinding>(this, R.layout.activity_track_status_delivery)
        val jsonMessenger = intent.getStringExtra(KEY_MESSENGER)
        val jsonDelivery = intent.getStringExtra(KEY_DELIVERY)
        val gson = GsonHolder.instance
        binder.delivery = gson.fromJson(jsonDelivery, EntregoDeliveryPreview::class.java)
        binder.messenger = gson.fromJson(jsonMessenger, EntregoMessengerView::class.java)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        mPresenter.buildSwitchListByState(binder.delivery.order.waypoints, getSwitchList())
    }

    fun getSwitchList(): List<ImageView> =
            listOf(delivery_states_on_way_sw,
                    delivery_states_waiting_for_ship_sw,
                    delivery_states_ship_on_way_sw,
                    delivery_states_waiting_for_delivery_sw,
                    delivery_states_delivered_sw)
}
