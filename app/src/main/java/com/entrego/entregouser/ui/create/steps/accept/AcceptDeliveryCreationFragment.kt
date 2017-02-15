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
import com.entrego.entregouser.ui.create.steps.accept.presenter.AcceptDeliveryPresenter
import com.entrego.entregouser.ui.create.steps.accept.presenter.IAcceptDeliveryPresenter
import com.entrego.entregouser.ui.create.steps.accept.view.IAcceptDeliveryView
import com.entrego.entregouser.util.loading
import com.entrego.entregouser.util.showSnack
import com.entrego.entregouser.web.model.response.delivery.create.DeliveryCreationResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_accept_delivery_creation.*

class AcceptDeliveryCreationFragment : Fragment(), IAcceptDeliveryView {


    companion object {
        val KEY_DELIVERY = "ext_k_delivery"
        val TAG = "AcceptDeliveryCreationFragment"
        fun getInstance(model: DeliveryCreationResponse): AcceptDeliveryCreationFragment {
            val fragment = AcceptDeliveryCreationFragment()
            val args = Bundle()
            args.putString(KEY_DELIVERY, Gson().toJson(model, DeliveryCreationResponse::class.java))
            fragment.arguments = args
            return fragment
        }
    }

    val mPresenter: IAcceptDeliveryPresenter = AcceptDeliveryPresenter()
    var mProgressView: ProgressDialog? = null
    var mDeliveryModel: DeliveryCreationResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        buildLayouts()
        mPresenter.onStart(this)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onStop()
    }

    fun buildLayouts() {
        accept_delivery_root_rl.setOnClickListener { }
        accept_delivery_price.text = mDeliveryModel?.sum?.toView()
        accept_delivery_type.text = mDeliveryModel?.deliveryType.toString()
        accept_delivery_accept_btn.setOnClickListener { mPresenter.acceptDelivery(mDeliveryModel?.deliveryId) }
    }


    override fun showProgress() {
        mProgressView = ProgressDialog(activity)
        mProgressView?.loading()
    }

    override fun hideProgress() {
        mProgressView?.dismiss()
    }

    override fun successAccept() {
        (activity as? RootActivityController)?.showCreatedDelivery()
    }
}