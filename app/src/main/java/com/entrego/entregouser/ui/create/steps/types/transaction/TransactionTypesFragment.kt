package com.entrego.entregouser.ui.create.steps.types.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entrego.entregouser.entity.delivery.EntregoServiceType
import com.entrego.entregouser.ui.create.mvp.model.FragmentType
import com.entrego.entregouser.ui.create.mvp.view.RootActivityController
import com.entrego.entregouser.ui.create.steps.BaseBuilderFragment
import com.entrego.entregouser.ui.create.steps.address.SelectAddressFragment
import kotlinx.android.synthetic.main.fragment_type_transaction.*

class TransactionTypesFragment : BaseBuilderFragment() {

    init {
        mDeliveryBuilder = DeliveryEntityBuilder()
        mDeliveryBuilder?.serviceType = EntregoServiceType.TRANSACTION
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_type_transaction, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        type_transaction_document.setOnClickListener {
            prepareNextFragment(SelectAddressFragment(), FragmentType.ADDRESS)
        }
        type_transaction_package.setOnClickListener {
            prepareNextFragment(SelectAddressFragment(), FragmentType.ADDRESS)
        }
    }
}