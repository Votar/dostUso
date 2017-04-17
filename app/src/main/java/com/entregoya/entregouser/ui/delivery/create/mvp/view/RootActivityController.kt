package com.entregoya.entregouser.ui.delivery.create.mvp.view

import android.app.Fragment
import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.ui.delivery.create.mvp.model.FragmentType

interface RootActivityController {
    fun showBuilderFragment(fragment: Fragment, type: FragmentType)
    fun showAcceptDeliveryCreationFragment(model: EntregoDeliveryPreview)
    fun showCreatedDelivery(delivery:EntregoDeliveryPreview?)
}