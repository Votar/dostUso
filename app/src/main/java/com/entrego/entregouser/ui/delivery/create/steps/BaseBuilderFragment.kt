package com.entrego.entregouser.ui.delivery.create.steps

import android.app.Fragment
import android.os.Bundle
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entrego.entregouser.ui.delivery.create.mvp.model.FragmentType
import com.entrego.entregouser.ui.delivery.create.mvp.view.RootActivityController
import com.entrego.entregouser.util.showSnack
import com.google.gson.Gson

abstract class BaseBuilderFragment : Fragment() {

    companion object {
        val KEY_BUILDER = "ext_k_builder"
    }

    protected var mDeliveryBuilder: DeliveryEntityBuilder? = null

    protected fun prepareNextFragment(fragment: BaseBuilderFragment, fragmentType: FragmentType) {
        val args = Bundle()
        val gson = Gson()
        if (mDeliveryBuilder != null) {
            args.putString(KEY_BUILDER, gson.toJson(mDeliveryBuilder, DeliveryEntityBuilder::class.java))
            fragment.arguments = args
            (activity as RootActivityController).showBuilderFragment(fragment, fragmentType)
        } else
            view.showSnack(getString(R.string.contact_support))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val jsonBuilder = arguments.getString(KEY_BUILDER, "")
            mDeliveryBuilder = Gson().fromJson(jsonBuilder, DeliveryEntityBuilder::class.java)
        }
    }


}