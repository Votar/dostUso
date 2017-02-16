package com.entrego.entregouser.ui.delivery.create.steps.building.category.deliver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entrego.entregouser.entity.delivery.EntregoServiceCategory
import com.entrego.entregouser.entity.delivery.EntregoSizeType
import com.entrego.entregouser.ui.delivery.create.mvp.model.FragmentType
import com.entrego.entregouser.ui.delivery.create.steps.BaseBuilderFragment
import com.entrego.entregouser.ui.delivery.create.steps.building.size.SelectSizeFragment
import com.entrego.entregouser.ui.delivery.create.steps.building.types.DeliveryTypesFragment
import kotlinx.android.synthetic.main.fragment_type_deliver.*

class DeliverBuyFragment : BaseBuilderFragment() {

    init {
        mDeliveryBuilder = DeliveryEntityBuilder()
        mDeliveryBuilder?.serviceType = EntregoServiceCategory.DELIVER
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
                mDeliveryBuilder?.sizeType = EntregoSizeType.SMALL
                prepareNextFragment(DeliveryTypesFragment(), FragmentType.PARAMETERS)
            }

            R.id.type_deliver_other -> {
                prepareNextFragment(SelectSizeFragment(), FragmentType.PARAMETERS)
            }

        }
    }
}