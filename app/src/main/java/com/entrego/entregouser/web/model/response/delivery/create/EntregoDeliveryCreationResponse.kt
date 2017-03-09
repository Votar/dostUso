package com.entrego.entregouser.web.model.response.delivery.create

import com.entrego.entregouser.entity.back.EntregoDeliveryPreview
import com.entrego.entregouser.web.model.response.BaseEntregoResponse


class EntregoDeliveryCreationResponse(code: Int?,
                                      message: String?,
                                      val payload: EntregoDeliveryPreview) : BaseEntregoResponse(code, message) {


    override fun toString(): String {
        return "EntregoDeliveryCreationResponse(payload=$payload, message=$message, code=$code)"
    }
}