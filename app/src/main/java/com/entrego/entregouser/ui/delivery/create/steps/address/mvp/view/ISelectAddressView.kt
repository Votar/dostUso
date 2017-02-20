package com.entrego.entregouser.ui.delivery.create.steps.address.mvp.view

import com.entrego.entregouser.entity.delivery.EntregoDelivery
import com.entrego.entregouser.web.model.response.delivery.create.EntregoDeliveryCreationResponse

interface ISelectAddressView {
    fun onShowMessage(strResId: Int)
    fun showErrorAddress(strResId: Int, index: Int)
    fun showProgress()
    fun hideProgress()
    fun showAcceptView(response: EntregoDelivery)
    fun onLogout()

}