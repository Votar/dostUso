package com.entrego.entregouser.ui.create.steps.types.shipment.all_day

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import kotlinx.android.synthetic.main.fragment_shipment_all_day.*

class SelectAllDayFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_shipment_all_day, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        shipment_all_day_before12.setOnClickListener { }
        shipment_all_day_after12.setOnClickListener { }
    }
}