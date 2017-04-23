package com.entregoya.entregouser.ui.delivery.create.steps.address

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.ui.autocomplete.EntregoAutocompleteActivity
import com.entregoya.entregouser.ui.delivery.create.mvp.view.IRootView
import com.entregoya.entregouser.ui.delivery.create.mvp.view.RootActivityController
import com.entregoya.entregouser.ui.delivery.create.steps.BaseBuilderFragment
import com.entregoya.entregouser.ui.delivery.create.steps.address.mvp.model.WayPointsAdapter
import com.entregoya.entregouser.ui.delivery.create.steps.address.mvp.presenter.SelectAddressPresenter
import com.entregoya.entregouser.ui.delivery.create.steps.address.mvp.view.FieldClickListener
import com.entregoya.entregouser.ui.delivery.create.steps.address.mvp.view.ISelectAddressView
import com.entregoya.entregouser.util.*
import kotlinx.android.synthetic.main.fragment_select_address.*
import java.util.*

class SelectAddressFragment : BaseBuilderFragment(), ISelectAddressView, FieldClickListener {


    var mProgessView: ProgressDialog? = null
    val mPresenter = SelectAddressPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_select_address, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        logd(mDeliveryBuilder.toString())
        select_address_recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        select_address_recycler.adapter = WayPointsAdapter(this)
        select_address_recycler.recycledViewPool.setMaxRecycledViews(0, 0)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart(this)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onStop()
    }

    private fun setupListeners() {
        select_address_add_address.setOnClickListener {
            (select_address_recycler.adapter as WayPointsAdapter)
                    .addPoint()
        }
        select_address_remove_address.setOnClickListener {
            (select_address_recycler.adapter as WayPointsAdapter)
                    .removePoint()
        }
        select_address_remove_address
        select_address_btn_more.setOnClickListener {
            it.visibility = View.GONE
            select_address_additional_ll.visibility = View.VISIBLE
        }
        select_address_from.setOnClickListener { openAutocompleteActivity(it as TextView) }
        select_address_to.setOnClickListener { openAutocompleteActivity(it as TextView) }

        select_address_swipe_btn.setOnClickListener {
            val tmpText = select_address_from.text
            select_address_from.text = select_address_to.text
            select_address_to.text = tmpText
        }

        select_address_accept_btn.setOnClickListener {

            val addressesList = ArrayList<String>()
            addressesList.add(select_address_from.text.toString())
            addressesList.add(select_address_to.text.toString())
            addressesList.addAll((select_address_recycler.adapter as WayPointsAdapter).getAddressList())
            mDeliveryBuilder?.addresses = addressesList
            mDeliveryBuilder?.returnFlag = select_address_return.isChecked
            select_address_promo.text.toString().apply {
                if (this.isNotEmpty())
                    mDeliveryBuilder?.promo = this
            }
            mDeliveryBuilder?.notes = select_address_special_field.text.toString()

            logd(mDeliveryBuilder.toString())
            mDeliveryBuilder?.let {
                mPresenter.requestDeliveryCreation(it)
            }
        }
    }

    var calledEditText: TextView? = null
    override fun openAutocompleteActivity(view: TextView) {

        // The autocomplete activity requires Google Play Services to be available. The intent
        // builder checks this and throws an exception if it is not the case.
        val cameraBounds = (activity as? IRootView)?.getCurrentFocusOnMap()
        val intent = EntregoAutocompleteActivity.getBuilder()
                .setBounds(cameraBounds)
                .build(activity)

        calledEditText = view
        startActivityForResult(intent, EntregoAutocompleteActivity.RQT_CODE)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check that the result was from the autocomplete widget.
        if (requestCode == EntregoAutocompleteActivity.RQT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val place = EntregoAutocompleteActivity.deserializeResult(data)
                    calledEditText?.text = place
                }
            }
        }
    }

    override fun showProgress() {
        mProgessView = ProgressDialog(activity)
        mProgessView?.loading()
    }

    override fun hideProgress() {
        mProgessView?.dismiss()
    }

    override fun showAcceptView(response: EntregoDeliveryPreview) {
        (activity as? RootActivityController)?.showAcceptDeliveryCreationFragment(response)
    }

    override fun onShowMessage(strResId: Int) {
        view.showSnack(getString(strResId))
    }

    override fun showErrorAddress(strResId: Int, index: Int) {
        view.showSnackError(getString(strResId, index + 1))
    }

    override fun onLogout() {
        activity?.logout()
    }
}