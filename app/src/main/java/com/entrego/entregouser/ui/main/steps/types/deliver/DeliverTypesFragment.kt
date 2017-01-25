package com.entrego.entregouser.ui.main.steps.types.deliver

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import kotlinx.android.synthetic.main.fragment_type_deliver.*

class DeliverTypesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_type_deliver, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        type_deliver_restaurant.setOnClickListener { }
        type_deliver_other.setOnClickListener { }
    }
}