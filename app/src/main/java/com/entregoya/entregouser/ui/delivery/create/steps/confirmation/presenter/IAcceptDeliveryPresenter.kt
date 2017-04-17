package com.entregoya.entregouser.ui.delivery.create.steps.confirmation.presenter

import com.entregoya.entregouser.ui.delivery.create.steps.confirmation.view.IAcceptDeliveryView

interface IAcceptDeliveryPresenter {
    fun onStart(view:IAcceptDeliveryView)
    fun onStop()
    fun acceptDelivery(deliveryId: Long)
}