package com.entrego.entregouser.ui.create.steps.address.mvp.view

import com.entrego.entregouser.web.model.response.delivery.create.DeliveryCreationResponse

interface ISelectAddressView {
    fun onShowMessage(idString: Int)
    fun showProgress()
    fun hideProgress()
    fun showAcceptView(response: DeliveryCreationResponse)
}