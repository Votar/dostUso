package com.entregoya.entregouser.web.model.response.delivery.create

import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse


class EntregoDeliveryCreationResponse(code: Int?,
                                      message: String?,
                                      val payload: EntregoDeliveryPreview) : BaseEntregoResponse(code, message) {


    override fun toString(): String {
        return "EntregoDeliveryCreationResponse(payload=$payload, message=$message, code=$code)"
    }
}