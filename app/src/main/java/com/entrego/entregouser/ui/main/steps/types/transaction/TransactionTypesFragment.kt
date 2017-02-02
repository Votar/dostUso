package com.entrego.entregouser.ui.main.steps.types.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entrego.entregouser.entity.delivery.EntregoServiceType
import com.entrego.entregouser.ui.main.mvp.view.RootActivityController
import com.entrego.entregouser.ui.main.steps.BaseBuilderFragment
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

        }
        type_transaction_package.setOnClickListener {
            (activity as RootActivityController).showSelectAddress()
        }
    }
}