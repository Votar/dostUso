package com.entregoya.entregouser.web.model.response.delivery

import com.entregoya.entregouser.entity.back.EntregoOrderView
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse


class EntregoGetStatusResult(code: Int?,
                             message: String?,
                             val payload: EntregoOrderView) : BaseEntregoResponse(code, message)