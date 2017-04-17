package com.entregoya.entregouser.web.model.response.profile

import com.entregoya.entregouser.storage.realm.models.CustomerProfileModel
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse

class GetProfileResponse(code:Int?, message:String?,
                         val payload:CustomerProfileModel) : BaseEntregoResponse(code, message){

    override fun toString(): String {
        return "GetProfileResponse(payload=$payload)"
    }
}
