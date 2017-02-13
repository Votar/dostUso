package com.entrego.entregouser.ui.create.steps.accept

import android.app.Fragment
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.create.mvp.view.RootActivityController
import com.entrego.entregouser.util.loading
import com.entrego.entregouser.util.showSnack
import com.entrego.entregouser.web.model.response.delivery.create.DeliveryCreationResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_accept_delivery_creation.*

class AcceptDeliveryCreationFragment : Fragment() {

    companion object {
        val KEY_DELIVERY = "ext_k_delivery"
        fun getInstance(model: DeliveryCreationResponse): AcceptDeliveryCreationFragment {
            val fragment = AcceptDeliveryCreationFragment()
            val args = Bundle()
            args.putString(KEY_DELIVERY, Gson().toJson(model, DeliveryCreationResponse::class.java))
            fragment.arguments = args
            return fragment
        }
    }

    var mProgressView: ProgressDialog? = null
    var mDeliveryModel: DeliveryCreationResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        if (arguments.getString(KEY_DELIVERY, "").isNotEmpty()) {
            val json = arguments.getString(KEY_DELIVERY)
            mDeliveryModel = Gson().fromJson(json, DeliveryCreationResponse::class.java)
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_accept_delivery_creation, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        accept_delivery_price.text = mDeliveryModel?.sum?.toView()
        accept_delivery_type.text = mDeliveryModel?.deliveryType.toString()
        accept_delivery_accept_btn.setOnClickListener { acceptDelivery(mDeliveryModel?.deliveryId) }
    }

    fun acceptDelivery(deliveryId: Long?) {
        if (deliveryId == null) {
            view.showSnack(getString(R.string.contact_support))
            return
        }

        showProgress()
        Handler().postDelayed({
            (activity as? RootActivityController)?.showCreatedDelivery()
            hideProgress()
        }, 1500)
    }

    fun showProgress() {
        mProgressView = ProgressDialog(activity)
        mProgressView?.loading()
    }

    fun hideProgress() {
        mProgressView?.dismiss()
    }
}