package com.entrego.entregouser.ui.delivery.create.mvp.view

import android.app.Fragment
import com.entrego.entregouser.entity.delivery.EntregoDelivery
import com.entrego.entregouser.ui.delivery.create.mvp.model.FragmentType
import com.entrego.entregouser.web.model.response.delivery.create.EntregoDeliveryCreationResponse

interface RootActivityController {
    fun showBuilderFragment(fragment: Fragment, type: FragmentType)
    fun showAcceptDeliveryCreationFragment(model: EntregoDelivery)
    fun showCreatedDelivery()
}