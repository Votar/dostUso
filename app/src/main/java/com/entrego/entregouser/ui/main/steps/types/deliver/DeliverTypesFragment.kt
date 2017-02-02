package com.entrego.entregouser.ui.main.steps.types.deliver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entrego.entregouser.entity.delivery.EntregoServiceType
import com.entrego.entregouser.ui.main.steps.BaseBuilderFragment
import kotlinx.android.synthetic.main.fragment_type_deliver.*

class DeliverTypesFragment : BaseBuilderFragment() {

    init {
        mDeliveryBuilder = DeliveryEntityBuilder()
        mDeliveryBuilder?.serviceType = EntregoServiceType.DELIVER
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_type_deliver, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        type_deliver_restaurant.setOnClickListener {

        }
        type_deliver_other.setOnClickListener { }
    }
}