package com.entrego.entregouser.ui.main.steps.types.shipment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entrego.entregouser.entity.delivery.EntregoServiceType
import com.entrego.entregouser.entity.delivery.EntregoTimingType
import com.entrego.entregouser.ui.main.steps.BaseBuilderFragment
import com.entrego.entregouser.ui.main.steps.types.shipment.size.SelectSizeFragment
import kotlinx.android.synthetic.main.fragment_type_shipment.*

class ShipmentTypesFragment : BaseBuilderFragment() {

    init {
        mDeliveryBuilder = DeliveryEntityBuilder()
        mDeliveryBuilder?.serviceType = EntregoServiceType.SHIPMENT
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_type_shipment, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        fragment_type_shipment.setOnClickListener { }
        type_shipment_express.setOnClickListener {
            mDeliveryBuilder?.timingType = EntregoTimingType.EXPRESS
            prepareNextFragment(SelectSizeFragment())
        }
        type_shipment_booking.setOnClickListener {
            mDeliveryBuilder?.timingType = EntregoTimingType.BOOKING
            prepareNextFragment(SelectSizeFragment())
        }
        type_shipment_all_day.setOnClickListener {
            mDeliveryBuilder?.timingType = EntregoTimingType.ALL_DAY
            prepareNextFragment(SelectSizeFragment())
        }
    }
}