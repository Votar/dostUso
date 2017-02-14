package com.entrego.entregouser.ui.create.steps.building.types

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entrego.entregouser.entity.delivery.EntregoServiceCategory
import com.entrego.entregouser.entity.delivery.EntregoTimingCategory
import com.entrego.entregouser.ui.create.mvp.model.FragmentType
import com.entrego.entregouser.ui.create.steps.BaseBuilderFragment
import com.entrego.entregouser.ui.create.steps.building.timing.all_day.SelectAllDayFragment
import com.entrego.entregouser.ui.create.steps.building.timing.booking.SelectBookingFragment
import com.entrego.entregouser.ui.create.steps.building.timing.express.ExpressSelectionFragment
import kotlinx.android.synthetic.main.fragment_type_shipment.*

class DeliveryTypesFragment : BaseBuilderFragment() {

    init {
        mDeliveryBuilder = DeliveryEntityBuilder()
        mDeliveryBuilder?.serviceType = EntregoServiceCategory.SHIPMENT
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_type_shipment, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        fragment_type_shipment.setOnClickListener { }
        type_shipment_express.setOnClickListener {
            mDeliveryBuilder?.timingType = EntregoTimingCategory.EXPRESS
            prepareNextFragment(ExpressSelectionFragment(), FragmentType.PARAMETERS)
        }
        type_shipment_booking.setOnClickListener {
            mDeliveryBuilder?.timingType = EntregoTimingCategory.BOOKING
            prepareNextFragment(SelectBookingFragment(), FragmentType.PARAMETERS)
        }
        type_shipment_all_day.setOnClickListener {
            mDeliveryBuilder?.timingType = EntregoTimingCategory.ALL_DAY
            prepareNextFragment(SelectAllDayFragment(), FragmentType.PARAMETERS)
        }
    }
}