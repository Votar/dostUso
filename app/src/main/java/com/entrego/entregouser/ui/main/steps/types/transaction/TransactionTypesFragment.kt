package com.entrego.entregouser.ui.main.steps.types.transaction

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.main.mvp.view.RootSelectAddressStep
import kotlinx.android.synthetic.main.fragment_type_transaction.*

class TransactionTypesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_type_transaction, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        type_transaction_document.setOnClickListener {
            (activity as RootSelectAddressStep).showSelectAddress()
        }
        type_transaction_package.setOnClickListener {
            (activity as RootSelectAddressStep).showSelectAddress()
        }
    }
}