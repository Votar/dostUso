package com.entrego.entregouser.ui.main.steps.types.shipment.size

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R

class ShipmentSizeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_shipment_size, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()

    }
}