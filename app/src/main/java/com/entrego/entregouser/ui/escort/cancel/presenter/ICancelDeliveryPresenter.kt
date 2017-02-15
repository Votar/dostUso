package com.entrego.entregouser.ui.escort.cancel.presenter

import android.support.v7.widget.RecyclerView
import com.entrego.entregouser.ui.escort.cancel.view.ICancelDeliveryView


interface ICancelDeliveryPresenter {
    fun onCreate(view: ICancelDeliveryView)
    fun onDestroy()
    fun setupRecyclerView(recycler: RecyclerView, reasons: List<String>)
}