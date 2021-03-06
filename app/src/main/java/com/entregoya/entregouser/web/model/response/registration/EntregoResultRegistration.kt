package com.entregoya.entregouser.web.model.response.registration

import com.entregoya.entregouser.web.model.response.BaseEntregoResponse
import com.entregoya.entregouser.web.model.response.common.FieldError


class EntregoResultRegistration(code: Int?,
                                message: String?,
                                val fields: Array<FieldError>) : BaseEntregoResponse(code, message)