package com.entregoya.entregouser.ui.delivery.escort.cancel.model

import android.support.annotation.Nullable
import com.entregoya.entregouser.web.api.ApiContract
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.request.delivery.cancel.CancelDeliveryBody
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse
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
                            response.body().apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK ->  listener?.onSuccessCancel()
                                    else -> listener?.onFailureCancel(message, code)
                                }
                            }
                        } else listener?.onFailureCancel(null, null)
                }

    })
}
}