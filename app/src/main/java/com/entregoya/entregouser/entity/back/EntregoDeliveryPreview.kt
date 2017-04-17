package com.entregoya.entregouser.entity.back

import com.entregoya.entregouser.entity.common.EntregoPriceEntity
import com.entregoya.entregouser.entity.delivery.EntregoDeliveryStatuses
import com.entregoya.entregouser.entity.delivery.EntregoParcel
import com.entregoya.entregouser.entity.delivery.EntregoServiceCategory
import com.entregoya.entregouser.entity.delivery.EntregoTimingCategory
import com.entregoya.entregouser.entity.route.EntregoRouteModel
import java.text.SimpleDateFormat
import java.util.*


class EntregoDeliveryPreview(val pickup: Long,
                             val parcel: EntregoParcel,
                             val status: EntregoDeliveryStatuses,
                             val category: EntregoServiceCategory,
                             val id: Long,
                             val order: EntregoOrderView?,
                             val type: EntregoTimingCategory,
                             val route: EntregoRouteModel,
                             val code:String,
                             val price: EntregoPriceEntity) {


    fun formattedPickup(): String {
        if (pickup <= 0) return ""
        val format = SimpleDateFormat("dd/MM/yy hh:mm aaa", Locale.getDefault())
        val timestamp = format.format(pickup * 1000)
        return timestamp
    }

    override fun toString(): String {
        return "EntregoDeliveryPreview(pickup=$pickup, parcel=$parcel, status=$status, category=$category, id=$id, order=$order, type=$type, route=$route, price=$price)"
    }


}