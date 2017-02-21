package com.entrego.entregouser.ui.auth.restore.model

import android.os.Handler
import com.entrego.entregouser.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.auth.RestorePasswordBody
import com.entrego.entregouser.web.model.response.CommonResponseListener
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

object RestoreModel {

    val request = ApiCreator.get().create(Request::class.java)
    private const val END_POINT = ""

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Body body: RestorePasswordBody): Call<BaseEntregoResponse>
    }


    fun restorePassword(email: String, listener: CommonResponseListener?) {
        //TODO:
        Handler().postDelayed({ listener?.onSuccessResponse() }, 1500)
//        val call: Call<BaseEntregoResponse> = request.parameters(RestorePasswordBody(email))
//        call.enqueue(object : Callback<BaseEntregoResponse> {
//            override fun onResponse(call: Call<BaseEntregoResponse>?, response: Response<BaseEntregoResponse>?) {
//                when (response?.body()?.code) {
//                    0 -> listener?.onSuccessResponse()
//                    else -> listener?.onFailureResponse(response?.body()?.code, response?.body()?.message)
//                }
//            }
//
//            override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
//                listener?.onFailureResponse(null, null)
//            }
//        })
    }
}