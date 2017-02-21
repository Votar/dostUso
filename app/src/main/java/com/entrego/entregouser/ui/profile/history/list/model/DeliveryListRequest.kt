package com.entrego.entregouser.ui.profile.history.list.model

import android.os.Handler
import com.entrego.entregouser.entity.common.EntregoPriceEntity
import com.entrego.entregouser.web.api.ApiCreator
import com.entrego.entregouser.web.model.response.delivery.list.EntregoDeliveryListResponse
import entrego.com.android.web.api.EntregoApi
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

class DeliveryListRequest {

    companion object {
        private const val END_POINT = "/"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body body: String): Call<EntregoDeliveryListResponse>
    }

    interface ResponseListener {
        fun onSuccessResponse(resultList: List<EntregoDeliveryPreview>)
        fun onFailureResponse(code: Int?, message: String?)
    }

    fun requestAsync(token: String, type: DeliveryListType, listener: ResponseListener?): Call<EntregoDeliveryListResponse> {
        val request = ApiCreator.get()
                .create(Request::class.java)
                .parameters(token, "")

        Handler().postDelayed({
            listener?.onSuccessResponse(listOf(
                    EntregoDeliveryPreview("http://irlen.com/wp-content/uploads/2015/09/world-map-large.png",
                            "12/12/2012",
                            "CANCELED",
                            EntregoPriceEntity(12.5f, "USD")),
                    EntregoDeliveryPreview("http://irlen.com/wp-content/uploads/2015/09/world-map-large.png",
                            "12/12/2012",
                            "CANCELED",
                            EntregoPriceEntity(12.5f, "USD"))
            ))
        }, 1500)
//        request.enqueue(object :Callback<EntregoDeliveryListResponse>{
//            override fun onFailure(call: Call<EntregoDeliveryListResponse>?, t: Throwable?) {
//
//            }
//
//            override fun onResponse(call: Call<EntregoDeliveryListResponse>?, response: Response<EntregoDeliveryListResponse>?) {
//
//            }
//        })

        return request
    }


}