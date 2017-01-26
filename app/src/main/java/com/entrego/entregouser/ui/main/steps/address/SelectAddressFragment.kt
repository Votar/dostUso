package com.entrego.entregouser.ui.main.steps.address

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.main.steps.address.mvp.view.ISelectAddressView
import kotlinx.android.synthetic.main.fragment_select_address.*

class SelectAddressFragment : Fragment(), ISelectAddressView {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_select_address, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        select_address_recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateAddressList() {

    }

    override fun onShowMessage(idString: Int) {

    }
}