package com.entregoya.entregouser.web.model.response


open class BaseEntregoResponse(open val code: Int?,
                               open val message: String?) {

    override fun toString(): String {
        return "BaseEntregoResponse(code=$code, message=$message)"
    }
}