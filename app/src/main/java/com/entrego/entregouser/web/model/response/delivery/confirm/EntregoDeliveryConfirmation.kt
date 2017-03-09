package com.entrego.entregouser.web.model.response.delivery.confirm

import com.entrego.entregouser.entity.back.EntregoDeliveryPreview
import com.entrego.entregouser.web.model.response.BaseEntregoResponse


class EntregoDeliveryConfirmation(code: Int?,
                                  message: String?,
                                  val payload: EntregoDeliveryPreview) : BaseEntregoResponse(code, message) {
    override fun toString(): String {
        return "EntregoDeliveryConfirmation(code=$code, message='$message', payload=$payload)"
    }
}