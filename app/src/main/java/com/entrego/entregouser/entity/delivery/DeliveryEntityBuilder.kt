package com.entrego.entregouser.entity.delivery


class DeliveryEntityBuilder {
    var serviceType: EntregoServiceCategory? = null
    var sizeType: EntregoSizeType? = null
    var timingType: EntregoTimingCategory? = null
    var executeTime: Long? = null
    override fun toString(): String {
        return "DeliveryEntityBuilder(serviceType=$serviceType, sizeType=$sizeType, timingType=$timingType)"
    }


}