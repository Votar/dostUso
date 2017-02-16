package com.entrego.entregouser.ui.delivery.create.steps.building.category.transaction

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
import kotlinx.android.synthetic.main.fragment_type_transaction.*

class TransactionTypesFragment : BaseBuilderFragment() {

    init {
        mDeliveryBuilder = DeliveryEntityBuilder()
        mDeliveryBuilder?.serviceType = EntregoServiceCategory.TRANSACTION
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_type_transaction, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        type_transaction_document.setOnClickListener {
            mDeliveryBuilder?.sizeType = EntregoSizeType.DOCUMENTS
            prepareNextFragment(DeliveryTypesFragment(), FragmentType.PARAMETERS)
        }
        type_transaction_package.setOnClickListener {
            prepareNextFragment(SelectSizeFragment(), FragmentType.PARAMETERS)
        }
    }
}