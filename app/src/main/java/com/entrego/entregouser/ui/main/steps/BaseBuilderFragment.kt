package com.entrego.entregouser.ui.main.steps

import android.app.Fragment
import android.os.Bundle
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entrego.entregouser.ui.main.mvp.view.RootActivityController
import com.entrego.entregouser.util.showSnack
import com.google.gson.Gson

abstract class BaseBuilderFragment : Fragment() {

    companion object {
        val KEY_BUILDER = "ext_k_builder"
    }

    protected var mDeliveryBuilder: DeliveryEntityBuilder? = null

    protected fun prepareNextFragment(fragment: BaseBuilderFragment) {
        val args = Bundle()
        val gson = Gson()
        if (mDeliveryBuilder != null) {
            args.putString(KEY_BUILDER, gson.toJson(mDeliveryBuilder, DeliveryEntityBuilder::class.java))
            fragment.arguments = args
            (activity as RootActivityController).showFragment(fragment)
        } else
            view.showSnack(getString(R.string.contact_support))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val jsonBuilder = arguments.getString(KEY_BUILDER, "")
            mDeliveryBuilder = Gson().fromJson(jsonBuilder, DeliveryEntityBuilder::class.java)
        }
    }


}