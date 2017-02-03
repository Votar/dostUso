package com.entrego.entregouser.ui.create.steps.accept.model

import android.os.Handler
import com.entrego.entregouser.web.model.request.delivery.create.DeliveryCreationModel
import com.entrego.entregouser.web.model.response.delivery.create.DeliveryCreationResponse
import com.entrego.entregouser.entity.common.EntregoPriceEntity
import com.entrego.entregouser.entity.delivery.EntregoTimingType
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.response.EntregoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


object RequestDeliveryConfirmation {

    private const val END_POINT = ""


    interface DeliveryConfirmationResponseListener {
        fun onSuccessCreationResponse(response: DeliveryCreationResponse)
        fun onFailureCreationResponse(code: Int?, message: String?)
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Body body: DeliveryCreationModel): Call<EntregoResponse>
    }

    fun requestAsync(listener: DeliveryConfirmationResponseListener) {
        Handler().postDelayed({
            val mockResponse = DeliveryCreationResponse(0, null, EntregoPriceEntity(12.5f), EntregoTimingType.EXPRESS, -1)
            listener.onSuccessCreationResponse(mockResponse)
        }, 1500)
    }
}