package com.entrego.entregouser.ui.main.steps.address

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.main.steps.address.mvp.model.WayPointsAdapter
import com.entrego.entregouser.ui.main.steps.address.mvp.presenter.SelectAddressPresenter
import com.entrego.entregouser.ui.main.steps.address.mvp.view.FieldClickListener
import com.entrego.entregouser.ui.main.steps.address.mvp.view.ISelectAddressView
import com.entrego.entregouser.util.logd
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import kotlinx.android.synthetic.main.fragment_select_address.*


class SelectAddressFragment : Fragment(), ISelectAddressView, FieldClickListener {

    companion object {
        val REQUEST_CODE_AUTOCOMPLETE = 0x122017
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    val mPresenter = SelectAddressPresenter()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_select_address, container, false)
        logd("ONCREATEVIEW")
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.onStart(this)
        logd("onViewCREATED")
        setupListeners()
        select_address_recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        select_address_recycler.adapter = WayPointsAdapter(this)
        select_address_recycler.recycledViewPool.setMaxRecycledViews(0, 0)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onStop()
    }

    private fun setupListeners() {
        select_address_multishipment_rl.setOnClickListener {
            (select_address_recycler.adapter as WayPointsAdapter)
                    .addPoint()
        }
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
            logd((select_address_recycler.adapter as WayPointsAdapter).getAddressList().toString())
        }

    }

    var calledEditText: TextView? = null
    override fun openAutocompleteActivity(view: TextView) {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(activity)
            calledEditText = view
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE)
        } catch (e: GooglePlayServicesRepairableException) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(activity, e.connectionStatusCode,
                    0 /* requestCode */).show()
        } catch (e: GooglePlayServicesNotAvailableException) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            val message = "Google Play Services is not available: " + GoogleApiAvailability.getInstance().getErrorString(e.errorCode)
            logd(message)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlaceAutocomplete.getPlace(activity, data)
                calledEditText?.text = place.address
            }
        }
    }


    override fun onShowMessage(idString: Int) {

    }
}