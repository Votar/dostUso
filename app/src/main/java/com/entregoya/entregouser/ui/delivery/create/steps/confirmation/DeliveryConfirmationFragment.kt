package com.entregoya.entregouser.ui.delivery.create.steps.confirmation

import android.app.Fragment
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.ui.delivery.create.steps.confirmation.presenter.AcceptDeliveryPresenter
import com.entregoya.entregouser.ui.delivery.create.steps.confirmation.presenter.IAcceptDeliveryPresenter
import com.entregoya.entregouser.ui.delivery.create.steps.confirmation.view.IAcceptDeliveryView
import com.entregoya.entregouser.ui.delivery.escort.root.EscortActivity
import com.entregoya.entregouser.util.GsonHolder
import com.entregoya.entregouser.util.loading
import com.entregoya.entregouser.util.logout
import com.entregoya.entregouser.util.showSnackError
import kotlinx.android.synthetic.main.fragment_accept_delivery_creation.*

class DeliveryConfirmationFragment : Fragment(), IAcceptDeliveryView {


    companion object {
        val KEY_DELIVERY = "ext_k_delivery"
        val TAG = "AcceptDeliveryCreationFragment"
        fun getInstance(model: EntregoDeliveryPreview): DeliveryConfirmationFragment {
            val fragment = DeliveryConfirmationFragment()
            val args = Bundle()
            args.putString(KEY_DELIVERY, GsonHolder.instance.toJson(model, EntregoDeliveryPreview::class.java))
            fragment.arguments = args
            return fragment
        }
    }

    val mPresenter: IAcceptDeliveryPresenter = AcceptDeliveryPresenter()
    var mProgressView: ProgressDialog? = null
    var mDeliveryModel: EntregoDeliveryPreview? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments.getString(KEY_DELIVERY, "").isNotEmpty()) {
            val json = arguments.getString(KEY_DELIVERY)
            mDeliveryModel = GsonHolder.instance.fromJson(json, EntregoDeliveryPreview::class.java)
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
        accept_delivery_price.text = mDeliveryModel?.price?.toView()
        accept_delivery_type.text = mDeliveryModel?.type.toString()
        accept_delivery_accept_btn.setOnClickListener {
            mDeliveryModel?.id?.apply { mPresenter.acceptDelivery(this) }
        }
    }


    override fun showProgress() {
        mProgressView = ProgressDialog(activity)
        mProgressView?.loading()
    }

    override fun hideProgress() {
        mProgressView?.dismiss()
    }

    override fun successAccept(confirmedDelivery: EntregoDeliveryPreview) {
//        (activity as? RootActivityController)?.showCreatedDelivery(mDeliveryModel)
        activity.fragmentManager.popBackStack()
        val intent = EscortActivity.getIntent(activity, confirmedDelivery)
        startActivity(intent)
    }

    override fun onLogout() {
        activity.logout()
    }

    override fun showError(message: String?) {
        view?.showSnackError(message)
    }

}