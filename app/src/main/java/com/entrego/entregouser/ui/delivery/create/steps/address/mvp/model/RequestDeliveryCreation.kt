package com.entrego.entregouser.ui.delivery.create.steps.address.mvp.model

import com.entrego.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entrego.entregouser.entity.delivery.EntregoDelivery
import com.entrego.entregouser.event.LogoutEvent
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.util.logd
import com.entrego.entregouser.web.model.request.delivery.create.DeliveryCreationModel
import com.entrego.entregouser.web.model.response.delivery.create.EntregoDeliveryCreationResponse
import com.google.common.eventbus.EventBus
import com.entrego.entregouser.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
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
        fun onSuccessCreationResponse(response: EntregoDelivery)
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
                response?.body()?.let {
                    when (it.code) {
                        0 -> {
                            listener.onSuccessCreationResponse(it.payload)
                            logd(it.toString())
                        }
                        2 -> listener.onFailureCreationResponse(it.code, it.message)
                    }
                }
            }

            override fun onFailure(call: Call<EntregoDeliveryCreationResponse>?, t: Throwable?) {
                call?.let {
                    if (call.isCanceled.not())
                        listener.onFailureCreationResponse(null, t?.message)
                }
            }

        })

//        Handler().postDelayed({
//            val mockResponse = EntregoDeliveryCreationResponse(0, null, EntregoPriceEntity(12.5f), EntregoTimingCategory.EXPRESS, -1)
//            listener.onSuccessCreationResponse(mockResponse)
//        }, 1500)

        return call
    }
}