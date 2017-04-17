package com.entregoya.entregouser.ui.delivery.finish.model

import android.support.annotation.Nullable
import com.entregoya.entregouser.web.api.ApiContract
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.request.delivery.FinishDeliveryBody
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*


object FinishDelivery {

    const val END_POINT = "customer/delivery/{deliveryId}/score/{orderId}"

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) toket: String,
                       @Path("deliveryId") deliveryId: Long,
                       @Path("orderId") orderId: Long,
                       @Body body: FinishDeliveryBody): Call<BaseEntregoResponse>
    }

    interface ResponseListener {
        fun onSuccess()
        fun onFailure(code: Int?, message: String?)
    }


    fun executeAsync(token: String, orderId: Long, deliveryId: Long, comment: String, rating: Int, @Nullable listener: ResponseListener?): Call<BaseEntregoResponse> {

        val body = FinishDeliveryBody(rating, comment)
        val request = ApiCreator.get()
                .create(Request::class.java)
                .parameters(token, deliveryId, orderId, body)

        request.enqueue(object : Callback<BaseEntregoResponse> {
            override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
                listener?.onFailure(null, null)
            }

            override fun onResponse(call: Call<BaseEntregoResponse>?, response: Response<BaseEntregoResponse>?) {
                if (response?.body() != null)
                    response.body().apply {
                        when (code) {
                            ApiContract.RESPONSE_OK -> listener?.onSuccess()
                            else -> listener?.onFailure(code, message)
                        }
                    } else listener?.onFailure(null, null)
            }
        })

        return request
    }
}