package com.entregoya.entregouser.ui.payment.card.list.model

import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.web.api.ApiContract
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.response.card.CardListResponse
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

class CardListRequest {
    companion object {
        const val END_POINT = "customer/payment/card"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String): Call<CardListResponse>
    }

    interface CardListListener {
        fun onSuccessCardList(resultList: Array<EntregoCreditCardEntity>)
        fun onFailureCardList(code: Int?, message: String?)
    }

    fun executeAsync(token: String, listener: CardListListener?) {
        ApiCreator.get()
                .create(Request::class.java)
                .parameters(token)
                .enqueue(object : Callback<CardListResponse> {
                    override fun onFailure(call: Call<CardListResponse>?, t: Throwable?) {
                        listener?.onFailureCardList(null, null)
                    }

                    override fun onResponse(call: Call<CardListResponse>?, response: Response<CardListResponse>?) {
                        if (response?.body() != null) {
                            response.body().apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> {
                                        listener?.onSuccessCardList(payload)
                                        EntregoStorage.saveCardList(payload.toList())
                                    }
                                    else -> listener?.onFailureCardList(code, message)
                                }
                            }
                        } else listener?.onFailureCardList(null, null)

                    }
                })

    }

}