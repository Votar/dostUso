package com.entregoya.entregouser.ui.delivery.create.steps.building.dummy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.entregouser.R
import com.entregoya.entregouser.ui.delivery.create.steps.BaseBuilderFragment

class SelectServiceFragment : BaseBuilderFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_select_service, container, false)
        return view
    }
}