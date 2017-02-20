package com.entrego.entregouser.web.model.response.delivery.create

import com.entrego.entregouser.entity.delivery.EntregoDelivery
import entrego.com.android.web.model.response.EntregoResponse


class EntregoDeliveryCreationResponse(code: Int?,
                                      message: String?,
                                      val payload: EntregoDelivery) : EntregoResponse(code, message) {


    override fun toString(): String {
        return "EntregoDeliveryCreationResponse(payload=$payload, message=$message, code=$code)"
    }
}