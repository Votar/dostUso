package com.entrego.entregouser.web.model.response.profile

import com.entrego.entregouser.storage.realm.models.CustomerProfileModel
import com.entrego.entregouser.web.model.response.BaseEntregoResponse

class GetProfileResponse(code:Int?, message:String?,
                         val payload:CustomerProfileModel) : BaseEntregoResponse(code, message){

    override fun toString(): String {
        return "GetProfileResponse(payload=$payload)"
    }
}
