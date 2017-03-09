package com.entrego.entregouser.ui.delivery.create.mvp.view

import android.app.Fragment
import com.entrego.entregouser.entity.back.EntregoDeliveryPreview
import com.entrego.entregouser.ui.delivery.create.mvp.model.FragmentType

interface RootActivityController {
    fun showBuilderFragment(fragment: Fragment, type: FragmentType)
    fun showAcceptDeliveryCreationFragment(model: EntregoDeliveryPreview)
    fun showCreatedDelivery(delivery:EntregoDeliveryPreview?)
}