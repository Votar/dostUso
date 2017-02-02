package com.entrego.entregouser.entity.delivery


class DeliveryEntityBuilder {
    var serviceType: EntregoServiceType? = null
    var sizeType: EntregoSizeType? = null
    var timingType: EntregoTimingType? = null
    override fun toString(): String {
        return "DeliveryEntityBuilder(serviceType=$serviceType, sizeType=$sizeType, timingType=$timingType)"
    }


}