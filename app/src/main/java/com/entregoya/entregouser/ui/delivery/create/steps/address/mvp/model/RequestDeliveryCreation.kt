package com.entregoya.entregouser.ui.delivery.create.steps.address.mvp.model

import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entregoya.entregouser.storage.preferences.PreferencesManager
import com.entregoya.entregouser.util.logd
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.response.delivery.create.EntregoDeliveryCreationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


object RequestDeliveryCreation {

    private const val END_POINT = "customer/delivery"


    interface DeliveryCreationResponseListener {
        fun onSuccessCreationResponse(response: EntregoDeliveryPreview)
        fun onFailureCreationResponse(code: Int?, message: String?)
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body body: DeliveryEntityBuilder): Call<EntregoDeliveryCreationResponse>
    }

    fun requestAsync(builder: DeliveryEntityBuilder, listener: DeliveryCreationResponseListener): Call<EntregoDeliveryCreationResponse> {

        val token = PreferencesManager.getTokenOrEmpty()
        val call = ApiCreator.get().create(Request::class.java)
                .parameters(token, builder)

        call.enqueue(object : Callback<EntregoDeliveryCreationResponse> {
            override fun onResponse(call: Call<EntregoDeliveryCreationResponse>?, response: Response<EntregoDeliveryCreationResponse>?) {

                if (response?.body() != null) {
                    logd(toString())
                    when (response.body().code) {
                        0 -> {
                            listener.onSuccessCreationResponse(response.body().payload)
                            logd(response.body().toString())
                        }
                        2 -> listener.onFailureCreationResponse(response.body().code,
                                response.body().message)
                    }
                } else listener.onFailureCreationResponse(null, null)
            }

            override fun onFailure(call: Call<EntregoDeliveryCreationResponse>?, t: Throwable?) {
                call?.let {
                    if (call.isCanceled.not())
                        listener.onFailureCreationResponse(null, t?.message)
                }
            }

        })

        return call
    }
}