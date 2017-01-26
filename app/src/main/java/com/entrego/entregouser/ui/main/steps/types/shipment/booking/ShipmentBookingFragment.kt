package com.entrego.entregouser.ui.main.steps.types.shipment.booking

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import kotlinx.android.synthetic.main.fragment_shipment_booking.*

class ShipmentBookingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_shipment_booking, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        booking_next_btn.setOnClickListener {
            activity.fragmentManager
                    .beginTransaction()
                    .replace(R.id.root_address_container, ShipmentBookingFragment())
                    .commit()
        }
    }
}