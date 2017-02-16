package com.entrego.entregouser.ui.delivery.create.steps.accept.presenter

import com.entrego.entregouser.ui.delivery.create.steps.accept.view.IAcceptDeliveryView

interface IAcceptDeliveryPresenter {
    fun onStart(view:IAcceptDeliveryView)
    fun onStop()
    fun acceptDelivery(deliveryId: Long?)
}