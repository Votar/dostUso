package com.entrego.entregouser.web.model.response.registration

import com.entrego.entregouser.web.model.response.common.FieldErrorResponse
import entrego.com.android.web.model.response.EntregoResponse


class EntregoResultRegistration(code: Int?,
                                message: String?,
                                val fields: Array<FieldErrorResponse>) : EntregoResponse(code, message) {

}