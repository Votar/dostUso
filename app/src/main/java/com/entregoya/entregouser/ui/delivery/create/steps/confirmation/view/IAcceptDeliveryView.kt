package com.entregoya.entregouser.ui.delivery.create.steps.confirmation.view

import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview


interface IAcceptDeliveryView {
    fun showProgress()
    fun hideProgress()
    fun successAccept(confirmedDelivery: EntregoDeliveryPreview)
    fun onLogout()
    fun showError(message :String?)
}