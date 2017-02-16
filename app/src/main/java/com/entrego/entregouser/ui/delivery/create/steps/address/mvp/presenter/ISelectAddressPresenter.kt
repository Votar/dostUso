package com.entrego.entregouser.ui.delivery.create.steps.address.mvp.presenter

import com.entrego.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entrego.entregouser.ui.delivery.create.steps.address.mvp.view.ISelectAddressView


interface ISelectAddressPresenter {

    fun onStart(view: ISelectAddressView)
    fun onStop()
    fun requestDeliveryCreation(builder: DeliveryEntityBuilder)

}