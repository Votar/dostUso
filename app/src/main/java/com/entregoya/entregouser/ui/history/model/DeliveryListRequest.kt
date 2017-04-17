package com.entregoya.entregouser.ui.history.model

import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.ui.history.list.model.DeliveryListType
import com.entregoya.entregouser.web.api.ApiContract
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.response.delivery.list.EntregoDeliveryListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

class DeliveryListRequest {

    companion object {
        private const val END_POINT = "customer/delivery"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String): Call<EntregoDeliveryListResponse>
    }

    interface ResponseListener {
        fun onSuccessResponse(resultList: List<EntregoDeliveryPreview>)
        fun onFailureResponse(code: Int?, message: String?)
    }

    fun requestAsync(token: String, type: DeliveryListType, listener: ResponseListener?): Call<EntregoDeliveryListResponse> {
        val request = ApiCreator.get()
                .create(Request::class.java)
                .parameters(token)

        request.enqueue(object : Callback<EntregoDeliveryListResponse> {
            override fun onFailure(call: Call<EntregoDeliveryListResponse>?, t: Throwable?) {
                listener?.onFailureResponse(null, t?.message)
            }

            override fun onResponse(call: Call<EntregoDeliveryListResponse>?, response: Response<EntregoDeliveryListResponse>?) {

                response?.body()?.apply {
                    when (code) {
                        ApiContract.RESPONSE_OK -> listener?.onSuccessResponse(payload.toList())
                        else -> listener?.onFailureResponse(code, message)
                    }
                }
            }
        })

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