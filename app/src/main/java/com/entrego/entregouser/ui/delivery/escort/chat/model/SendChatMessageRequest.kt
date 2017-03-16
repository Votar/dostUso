package com.entrego.entregouser.ui.delivery.escort.chat.model

import com.entrego.entregouser.web.api.ApiCreator
import com.entrego.entregouser.web.model.request.chat.ChatMessageBody
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import entrego.com.android.web.api.EntregoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

class SendChatMessageRequest {

    companion object {
        private const val END_POINT = "common/chat/order"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body message: ChatMessageBody): Call<BaseEntregoResponse>
    }

    interface ResponseListener {
        fun onSuccessResponse()
        fun onFailureResponse(code: Int?, message: String?)
    }

    fun requestAsync(token: String, message: ChatMessageBody, listener: ResponseListener?): Call<BaseEntregoResponse> {
        val request = ApiCreator.get()
                .create(Request::class.java)
                .parameters(token, message)

        request.enqueue(object : Callback<BaseEntregoResponse> {
            override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
                listener?.onFailureResponse(null, t?.message)
            }

            override fun onResponse(call: Call<BaseEntregoResponse>?, response: Response<BaseEntregoResponse>?) {
                if (response != null)
                    response.body().apply {
                        when (code) {
                            0 -> listener?.onSuccessResponse()
                            else -> listener?.onFailureResponse(code, this.message)
                        }
                    }
                else
                    listener?.onFailureResponse(null, null)
            }
        })

        return request
    }


}