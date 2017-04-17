package com.entregoya.entregouser.ui.delivery.escort.root.model

import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.response.delivery.confirm.EntregoDeliveryConfirmation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

class GetDeliveryRequest {

    companion object {
        private const val END_POINT = "customer/delivery/{id}"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Path("id") deliveryId: Long): Call<EntregoDeliveryConfirmation>
    }

    interface ResponseListener {
        fun onSuccessResponse(result: EntregoDeliveryPreview)
        fun onFailureResponse(code: Int?, message: String?)
    }

    fun requestAsync(token: String, deliveryId: Long, listener: ResponseListener?): Call<EntregoDeliveryConfirmation> {
        val request = ApiCreator.get()
                .create(Request::class.java)
                .parameters(token, deliveryId)

        request.enqueue(object : Callback<EntregoDeliveryConfirmation> {
            override fun onFailure(call: Call<EntregoDeliveryConfirmation>?, t: Throwable?) {
                listener?.onFailureResponse(null, t?.message)
            }

            override fun onResponse(call: Call<EntregoDeliveryConfirmation>?, response: Response<EntregoDeliveryConfirmation>?) {
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