package com.entrego.entregouser.ui.delivery.escort.cancel.model

import android.support.annotation.Nullable
import com.entrego.entregouser.web.api.ApiContract
import com.entrego.entregouser.web.api.ApiCreator
import com.entrego.entregouser.web.model.request.delivery.cancel.CancelDeliveryBody
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import entrego.com.android.web.api.EntregoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*


object CancelDelivery {

    const val END_POINT = "customer/delivery/{id}/cancel"

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String,
                       @Path("id") deliveryId: Long,
                       @Body body: CancelDeliveryBody): Call<BaseEntregoResponse>
    }

    interface CancelDeliveryListener {
        fun onSuccessCancel()
        fun onFailureCancel(message: String?, code: Int?)
    }

    var isRequested: Boolean = false

    fun executeAsync(token: String, deliveryId: Long, reason: String, @Nullable listener: CancelDeliveryListener?) {

        if (isRequested) return

        ApiCreator.get()
                .create(Request::class.java)
                .parameters(token, deliveryId, CancelDeliveryBody(reason))
                .enqueue(object : Callback<BaseEntregoResponse> {
                    override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
                        listener?.onFailureCancel(null, null)
                    }

                    override fun onResponse(call: Call<BaseEntregoResponse>?, response: Response<BaseEntregoResponse>?) {
                        if (response?.body() != null) {
                            when (response.body().code) {
                                ApiContract.RESPONSE_OK -> {
                                    listener?.onSuccessCancel()
                                }
                                else -> listener?.onFailureCancel(response.body().message,response.body().code)
                            }
                        } else listener?.onFailureCancel(null, null)
                }

    })
}
}