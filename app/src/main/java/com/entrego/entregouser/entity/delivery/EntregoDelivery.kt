package com.entrego.entregouser.entity.delivery

import com.entrego.entregouser.entity.common.EntregoPriceEntity
import com.entrego.entregouser.entity.route.EntregoRouteModel

class EntregoDelivery(val category: EntregoServiceCategory,
                           val pickup: Long,
                           val parcel: EntregoParcelType,
                           val type: EntregoTimingCategory,
                           val price: EntregoPriceEntity,
                           val route: EntregoRouteModel,
                           val id: Long) {

    override fun toString(): String {
        return "EntregoDelivery(category=$category, pickup=$pickup, parcel=$parcel, type=$type, price=$price, route=$route, id=$id)"
    }
}