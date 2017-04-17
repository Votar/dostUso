package com.entregoya.entregouser.ui.delivery.create.steps.address.mvp.presenter

import com.entregoya.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entregoya.entregouser.ui.delivery.create.steps.address.mvp.view.ISelectAddressView


interface ISelectAddressPresenter {

    fun onStart(view: ISelectAddressView)
    fun onStop()
    fun requestDeliveryCreation(builder: DeliveryEntityBuilder)
}