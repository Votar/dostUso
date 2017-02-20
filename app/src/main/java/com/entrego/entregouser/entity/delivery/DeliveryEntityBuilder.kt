package com.entrego.entregouser.entity.delivery


class DeliveryEntityBuilder {
    var category: EntregoServiceCategory? = null
    var parcel: EntregoParcelType? = null
    var type: EntregoTimingCategory? = null
    var pickup: Long? = null
    var addresses: List<String>? = null
    override fun toString(): String {
        return "DeliveryEntityBuilder(category=$category, parcel=$parcel, type=$type, pickup=$pickup, addresses=$addresses)"
    }


}