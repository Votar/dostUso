package com.entrego.entregouser.web.model.response.delivery.create

import com.entrego.entregouser.entity.common.EntregoPriceEntity
import com.entrego.entregouser.entity.delivery.EntregoServiceCategory
import com.entrego.entregouser.entity.delivery.EntregoTimingCategory
import entrego.com.android.web.model.response.EntregoResponse


class DeliveryCreationResponse(code: Int?,
                               message: String?,
                               val sum: EntregoPriceEntity,
                               val deliveryType: EntregoTimingCategory,
                               val deliveryId: Long) : EntregoResponse(code, message) {

}