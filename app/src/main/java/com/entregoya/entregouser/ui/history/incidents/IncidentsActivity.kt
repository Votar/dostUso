package com.entregoya.entregouser.ui.history.incidents

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.entity.back.EntregoWaypoint
import com.entregoya.entregouser.entity.common.EntregoMessengerView
import com.entregoya.entregouser.entity.route.EntregoRouteModel
import com.entregoya.entregouser.ui.faq.FaqAdapter
import com.entregoya.entregouser.ui.faq.FaqDetailActivity
import com.entregoya.entregouser.util.GsonHolder
import com.entregoya.entregouser.util.getStaticMapUrlWithWaypoints
import com.entregoya.entregouser.util.loadMessengerPicWithToken
import kotlinx.android.synthetic.main.activity_incidents.*
import kotlinx.android.synthetic.main.include_delivery_route.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import java.util.*

class IncidentsActivity : AppCompatActivity() {

    companion object {
        const val KEY_DELIVERY = "ext_k_delivery"
        fun getIntent(ctx: Context, delivery: EntregoDeliveryPreview): Intent {
            val intent = Intent(ctx, IncidentsActivity::class.java)
            val jsonDelivery = GsonHolder.instance.toJson(delivery, EntregoDeliveryPreview::class.java)
            intent.putExtra(KEY_DELIVERY, jsonDelivery)
            return intent
        }

        fun getIntent(ctx: Context, delivery: String): Intent {
            val intent = Intent(ctx, IncidentsActivity::class.java)
            intent.putExtra(KEY_DELIVERY, delivery)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incidents)
        deserializeIntent()
        setupLayouts()
        setupFaqList()

    }

    fun deserializeIntent() {
        if (intent.hasExtra(KEY_DELIVERY)) {
            val json = intent.getStringExtra(KEY_DELIVERY)
            val delivery = GsonHolder.instance.fromJson(json, EntregoDeliveryPreview::class.java)
            if (delivery.order != null) {
                setupAddressList(delivery.order.waypoints.toList())
                setupMessengerView(delivery.order.messenger)
            }
            setupStaticMap(delivery.route)
            incidents_time.text = delivery.formattedPickup()
        } else throw IllegalStateException("No delivery in intent")
    }

    private fun setupLayouts() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
    }

    fun setupAddressList(waypoints: List<EntregoWaypoint>) {
        status_address_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        status_address_recycler.adapter = IncidentsAddressListAdapter(waypoints)
    }

    fun setupStaticMap(route: EntregoRouteModel) {
        val urlStaticMap = route.getStaticMapUrlWithWaypoints()
        Glide.with(this)
                .load(urlStaticMap)
                .error(R.drawable.ic_cloud_off_48dp)
                .into(incidents_routes_static_map)
    }

    fun setupMessengerView(messenger: EntregoMessengerView) {
        incidents_messenger_name.text = messenger.name
        incidents_messenger_photo.loadMessengerPicWithToken(messenger.id)
    }

    fun setupFaqList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        incidents_faq_recycler.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        incidents_faq_recycler.addItemDecoration(dividerItemDecoration)

        val titles = resources.getStringArray(R.array.incidents_faq_titles)
        val bodies = resources.getStringArray(R.array.incidents_faq_bodies)
        val faqList: ArrayList<Pair<String, String>> = ArrayList()
        for (i in 0..titles.size - 1)
            faqList.add(Pair(titles[i], bodies[i]))
        incidents_faq_recycler.adapter = FaqAdapter(faqList, faqClickListener)
    }


    val faqClickListener = object : FaqAdapter.FaqClickListener {
        override fun onFaqClicked(title: String, message: String) {
            val intent = Intent(applicationContext, FaqDetailActivity::class.java)
            intent.putExtra(FaqDetailActivity.EXT_TITLE, title)
            intent.putExtra(FaqDetailActivity.EXT_MSG, message)
            startActivity(intent)
        }
    }


}
