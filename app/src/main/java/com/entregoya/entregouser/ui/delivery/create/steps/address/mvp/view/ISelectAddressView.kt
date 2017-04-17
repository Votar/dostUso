package com.entregoya.entregouser.ui.delivery.create.steps.address.mvp.view

import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview

interface ISelectAddressView {
    fun onShowMessage(strResId: Int)
    fun showErrorAddress(strResId: Int, index: Int)
    fun showProgress()
    fun hideProgress()
    fun showAcceptView(response: EntregoDeliveryPreview)
    fun onLogout()

}