package com.entrego.entregouser.ui.main.steps.types.shipment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.main.steps.types.shipment.size.ShipmentSizeFragment
import kotlinx.android.synthetic.main.fragment_type_shipment.*

class ShipmentTypesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_type_shipment, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        fragment_type_shipment.setOnClickListener { }
        type_shipment_express.setOnClickListener {
            activity.fragmentManager
                    .beginTransaction()
                    .replace(R.id.root_builder_container, ShipmentSizeFragment())
                    .commit()
        }
        type_shipment_booking.setOnClickListener {

        }
        type_shipment_all_day.setOnClickListener { }
    }
}