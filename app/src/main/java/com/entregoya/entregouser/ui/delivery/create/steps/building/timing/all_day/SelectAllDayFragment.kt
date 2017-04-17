package com.entregoya.entregouser.ui.delivery.create.steps.building.timing.all_day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.entregouser.R
import com.entregoya.entregouser.ui.delivery.create.mvp.model.FragmentType
import com.entregoya.entregouser.ui.delivery.create.steps.BaseBuilderFragment
import com.entregoya.entregouser.ui.delivery.create.steps.address.SelectAddressFragment
import kotlinx.android.synthetic.main.fragment_shipment_all_day.*

class SelectAllDayFragment : BaseBuilderFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_shipment_all_day, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        shipment_all_day_before12.setOnClickListener{
            mDeliveryBuilder?.pickup = 0
            showNext()
        }

        shipment_all_day_after12.setOnClickListener{
            mDeliveryBuilder?.pickup = 1
            showNext()
        }
    }


    fun showNext(){
        prepareNextFragment(SelectAddressFragment(), FragmentType.ADDRESS)
    }
}