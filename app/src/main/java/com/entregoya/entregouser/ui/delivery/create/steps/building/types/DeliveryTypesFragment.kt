package com.entregoya.entregouser.ui.delivery.create.steps.building.types

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entregoya.entregouser.entity.delivery.EntregoServiceCategory
import com.entregoya.entregouser.entity.delivery.EntregoTimingCategory
import com.entregoya.entregouser.ui.delivery.create.mvp.model.FragmentType
import com.entregoya.entregouser.ui.delivery.create.steps.BaseBuilderFragment
import com.entregoya.entregouser.ui.delivery.create.steps.building.timing.all_day.SelectAllDayFragment
import com.entregoya.entregouser.ui.delivery.create.steps.building.timing.booking.SelectBookingFragment
import com.entregoya.entregouser.ui.delivery.create.steps.building.timing.express.ExpressSelectionFragment
import kotlinx.android.synthetic.main.fragment_type_shipment.*

class DeliveryTypesFragment : BaseBuilderFragment() {

    init {
        mDeliveryBuilder = DeliveryEntityBuilder()
        mDeliveryBuilder?.category = EntregoServiceCategory.SHIPMENT
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_type_shipment, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        fragment_type_shipment.setOnClickListener { }
        type_shipment_express.setOnClickListener {
            mDeliveryBuilder?.type = EntregoTimingCategory.EXPRESS
            prepareNextFragment(ExpressSelectionFragment(), FragmentType.PARAMETERS)
        }
        type_shipment_booking.setOnClickListener {
            mDeliveryBuilder?.type = EntregoTimingCategory.BOOKING
            prepareNextFragment(SelectBookingFragment(), FragmentType.PARAMETERS)
        }
        type_shipment_all_day.setOnClickListener {
            mDeliveryBuilder?.type = EntregoTimingCategory.ALLDAY
            prepareNextFragment(SelectAllDayFragment(), FragmentType.PARAMETERS)
        }
    }
}