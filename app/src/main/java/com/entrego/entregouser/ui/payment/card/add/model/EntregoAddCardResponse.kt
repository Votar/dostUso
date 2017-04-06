package com.entrego.entregouser.ui.payment.card.add.model

import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import com.entrego.entregouser.web.model.response.common.FieldError


class EntregoAddCardResponse(code: Int,
                             message: String,
                             val fields: Array<FieldError>) : BaseEntregoResponse(code, message)
