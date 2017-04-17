package com.entregoya.entregouser.ui.delivery.create.steps.building.category.deliver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entregoya.entregouser.entity.delivery.EntregoParcel
import com.entregoya.entregouser.entity.delivery.EntregoServiceCategory
import com.entregoya.entregouser.ui.delivery.create.mvp.model.FragmentType
import com.entregoya.entregouser.ui.delivery.create.steps.BaseBuilderFragment
import com.entregoya.entregouser.ui.delivery.create.steps.building.size.SelectSizeFragment
import com.entregoya.entregouser.ui.delivery.create.steps.building.types.DeliveryTypesFragment
import kotlinx.android.synthetic.main.fragment_type_deliver.*

class DeliverBuyFragment : BaseBuilderFragment() {

    init {
        mDeliveryBuilder = DeliveryEntityBuilder()
        mDeliveryBuilder?.category = EntregoServiceCategory.BUYDELIVER
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_type_deliver, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        type_deliver_restaurant.setOnClickListener(mClickListener)
        type_deliver_other.setOnClickListener(mClickListener)
    }

    val mClickListener = View.OnClickListener {
        when (it.id) {
            R.id.type_deliver_restaurant -> {
                mDeliveryBuilder?.parcel = EntregoParcel.SMALL
                prepareNextFragment(DeliveryTypesFragment(), FragmentType.PARAMETERS)
            }

            R.id.type_deliver_other -> {
                prepareNextFragment(SelectSizeFragment(), FragmentType.PARAMETERS)
            }

        }
    }
}