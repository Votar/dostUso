package com.entrego.entregouser.web.model.response.registration

import com.entrego.entregouser.web.model.response.common.FieldErrorResponse
import com.entrego.entregouser.web.model.response.BaseEntregoResponse


class EntregoResultRegistration(code: Int?,
                                message: String?,
                                val fields: Array<FieldErrorResponse>) : BaseEntregoResponse(code, message) {

}