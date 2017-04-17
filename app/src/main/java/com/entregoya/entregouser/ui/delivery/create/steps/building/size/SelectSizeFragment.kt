package com.entregoya.entregouser.ui.delivery.create.steps.building.size

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.delivery.EntregoParcel
import com.entregoya.entregouser.ui.delivery.create.mvp.model.FragmentType
import com.entregoya.entregouser.ui.delivery.create.steps.BaseBuilderFragment
import com.entregoya.entregouser.ui.delivery.create.steps.building.types.DeliveryTypesFragment
import kotlinx.android.synthetic.main.fragment_shipment_size.*

class SelectSizeFragment : BaseBuilderFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_shipment_size, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        size_builder_small.setOnClickListener(mClickListener)
        size_builder_large.setOnClickListener(mClickListener)
    }

    val mClickListener = View.OnClickListener { view ->
        when (view?.id) {
            R.id.size_builder_small -> mDeliveryBuilder?.parcel = EntregoParcel.SMALL
            R.id.size_builder_large -> mDeliveryBuilder?.parcel = EntregoParcel.LARGE
        }
        prepareNextFragment(DeliveryTypesFragment(), FragmentType.PARAMETERS)
    }

}