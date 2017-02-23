package com.entrego.entregouser.web.model.response.delivery

import com.entrego.entregouser.entity.common.EntregoStatusModel
import com.entrego.entregouser.web.model.response.BaseEntregoResponse


class EntregoGetStatusResult(code: Int?,
                             message: String?,
                             val payload: EntregoStatusModel) : BaseEntregoResponse(code, message) {
}