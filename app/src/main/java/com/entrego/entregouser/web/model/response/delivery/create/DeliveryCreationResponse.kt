package com.entrego.entregouser.web.model.response.delivery.create

import com.entrego.entregouser.entity.common.EntregoPriceEntity
import com.entrego.entregouser.entity.delivery.EntregoServiceType
import com.entrego.entregouser.entity.delivery.EntregoTimingType
import entrego.com.android.web.model.response.EntregoResponse


class DeliveryCreationResponse(code: Int?,
                               message: String?,
                               val sum: EntregoPriceEntity,
                               val deliveryType: EntregoTimingType,
                               val deliveryId: Long) : EntregoResponse(code, message) {

}