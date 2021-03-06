package com.entregoya.entregouser.ui.delivery.escort.cancel.view

import android.content.Context


interface ICancelDeliveryView {
    fun onShowProgress()
    fun onHideProgress()
    fun showMessage(message: String?)
    fun getActivityContext(): Context
    fun showSuccessScreen()
}