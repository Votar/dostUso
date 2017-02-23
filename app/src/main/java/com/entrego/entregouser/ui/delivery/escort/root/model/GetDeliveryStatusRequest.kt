package com.entrego.entregouser.ui.delivery.escort.root.model

import com.entrego.entregouser.entity.common.EntregoStatusModel
import com.entrego.entregouser.web.api.ApiCreator
import com.entrego.entregouser.web.model.response.delivery.EntregoGetStatusResult
import entrego.com.android.web.api.EntregoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

class GetDeliveryStatusRequest {

    companion object {
        private const val END_POINT = "customer/delivery/{id}/status"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Path("id") deliveryId: Long): Call<EntregoGetStatusResult>
    }

    interface ResponseListener {
        fun onSuccessResponse(result: EntregoStatusModel)
        fun onFailureResponse(code: Int?, message: String?)
    }

    fun requestAsync(token: String, deliveryId: Long, listener: ResponseListener?): Call<EntregoGetStatusResult> {
        val request = ApiCreator.get()
                .create(Request::class.java)
                .parameters(token, deliveryId)

        request.enqueue(object : Callback<EntregoGetStatusResult> {
            override fun onFailure(call: Call<EntregoGetStatusResult>?, t: Throwable?) {
                listener?.onFailureResponse(null, t?.message)
            }

            override fun onResponse(call: Call<EntregoGetStatusResult>?, response: Response<EntregoGetStatusResult>?) {
                if (response != null)
                    response.body().apply {
                        when (code) {
                        0 -> listener?.onSuccessResponse(payload)
                        else -> listener?.onFailureResponse(code, message)
                    }
                    }
                else
                    listener?.onFailureResponse(null, null)
            }
        })

        return request
    }


}