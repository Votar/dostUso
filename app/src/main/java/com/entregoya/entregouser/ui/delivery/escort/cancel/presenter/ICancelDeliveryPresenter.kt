package com.entregoya.entregouser.ui.delivery.escort.cancel.presenter

import android.support.v7.widget.RecyclerView
import com.entregoya.entregouser.ui.delivery.escort.cancel.view.ICancelDeliveryView


interface ICancelDeliveryPresenter {
    fun onCreate(view: ICancelDeliveryView)
    fun onDestroy()
    fun setupRecyclerView(recycler: RecyclerView, deliveryId:Long, reasons: List<String>)
}