package com.entrego.entregouser.web.model.response.delivery

import com.entrego.entregouser.entity.back.EntregoOrderView
import com.entrego.entregouser.web.model.response.BaseEntregoResponse


class EntregoGetStatusResult(code: Int?,
                             message: String?,
                             val payload: EntregoOrderView) : BaseEntregoResponse(code, message)