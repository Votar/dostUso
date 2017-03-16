package com.entrego.entregouser.entity.delivery

import com.google.gson.annotations.SerializedName


class DeliveryEntityBuilder {
    var category: EntregoServiceCategory? = null
    var parcel: EntregoParcel? = null
    var type: EntregoTimingCategory? = null
    var pickup: Long? = null
    var addresses: List<String>? = null
    @SerializedName("return")
    var returnFlag : Boolean = false
    override fun toString(): String {
        return "DeliveryEntityBuilder(category=$category, parcel=$parcel, type=$type, pickup=$pickup, addresses=$addresses)"
    }


}