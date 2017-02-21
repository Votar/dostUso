package com.entrego.entregouser.web.model.response

interface CommonResponseListener {
    fun onSuccessResponse()
    fun onFailureResponse(code:Int?, message:String?)
}