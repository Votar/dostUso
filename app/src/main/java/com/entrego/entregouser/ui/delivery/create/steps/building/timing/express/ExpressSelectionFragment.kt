package com.entrego.entregouser.ui.delivery.create.steps.building.timing.express

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.delivery.create.mvp.model.FragmentType
import com.entrego.entregouser.ui.delivery.create.steps.BaseBuilderFragment
import com.entrego.entregouser.ui.delivery.create.steps.address.SelectAddressFragment
import com.entrego.entregouser.ui.delivery.create.steps.building.size.SelectSizeFragment
import kotlinx.android.synthetic.main.fragment_express_selection.*
import java.util.*

class ExpressSelectionFragment : BaseBuilderFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_express_selection, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        express_builder_now.setOnClickListener { showNextFragment(0) }
        express_builder_30.setOnClickListener { showNextFragment(30) }
        express_builder_45.setOnClickListener { showNextFragment(45) }
        express_builder_60.setOnClickListener { showNextFragment(60) }
    }

    fun showNextFragment(offsetMin: Int) {
        mDeliveryBuilder?.executeTime = Date().time
        prepareNextFragment(SelectAddressFragment(), FragmentType.ADDRESS)
    }


}