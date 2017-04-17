package com.entregoya.entregouser.ui.delivery.create.steps.confirmation.model

import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.util.logd
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.response.delivery.confirm.EntregoDeliveryConfirmation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


object RequestDeliveryConfirmation {

    private const val END_POINT = "customer/delivery/{id}/confirm"


    interface DeliveryConfirmationResponseListener {
        fun onSuccessCreationResponse(deliveryView: EntregoDeliveryPreview)
        fun onFailureCreationResponse(code: Int?, message: String?)
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Path("id") deliveryId: Long, @Header(EntregoApi.TOKEN) token: String): Call<EntregoDeliveryConfirmation>
    }

    fun requestAsync(deliveryId: Long, token: String, listener: DeliveryConfirmationResponseListener?) {
        val call = ApiCreator.get().create(Request::class.java)
                .parameters(deliveryId, token)
                .enqueue(object : Callback<EntregoDeliveryConfirmation> {
                    override fun onFailure(call: Call<EntregoDeliveryConfirmation>?, t: Throwable?) {
                        listener?.onFailureCreationResponse(null, t?.message)
                    }

                    override fun onResponse(call: Call<EntregoDeliveryConfirmation>?, response: Response<EntregoDeliveryConfirmation>?) {
                        response?.body()?.apply {
                            logd(toString())
                            when (code) {
                                0 -> listener?.onSuccessCreationResponse(payload)
                                else -> listener?.onFailureCreationResponse(code, message)
                            }
                        }
                    }
                })


    }
}