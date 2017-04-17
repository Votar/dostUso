package com.entrego.entregouser.ui.intro.registration.model

import com.entrego.entregouser.entity.EntregoPhoneModel
import com.entrego.entregouser.web.api.ApiCreator
import com.entrego.entregouser.web.api.EntregoApi
import com.entrego.entregouser.web.model.request.registration.RegistrationFbBody
import com.entrego.entregouser.web.model.response.CommonResponseListener
import com.entrego.entregouser.web.model.response.common.FieldError
import com.entrego.entregouser.web.model.response.registration.EntregoResultRegistration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


class RegistrationWithFbRequest(val email: String,
                                val name: String,
                                val fbToken: String,
                                val phoneCode: String,
                                val phoneNumber: String) {

    interface ResultListener : CommonResponseListener {
        fun onValidationError(field: FieldError)
    }

    companion object {
        private const val END_POINT = "customer/user/register"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Body body: RegistrationFbBody): Call<EntregoResultRegistration>
    }

    fun requestAsync(listener: ResultListener) {

        val body = RegistrationFbBody(email, name, fbToken, EntregoPhoneModel(phoneCode, phoneNumber))

        ApiCreator.get().create(Request::class.java)
                .parameters(body)
                .enqueue(object : Callback<EntregoResultRegistration> {
                    override fun onResponse(call: Call<EntregoResultRegistration>?, response: Response<EntregoResultRegistration>?) {
                        if (response?.body() != null) {
                            response.body().apply {
                                when (code) {
                                    0 -> listener.onSuccessResponse()
                                    1 -> if (fields.isNotEmpty()) {
                                        for (next in fields)
                                            listener.onValidationError(next)
                                    }
                                    else -> listener.onFailureResponse(code, message)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResultRegistration>?, t: Throwable?) {
                        listener.onFailureResponse(null, t?.message)
                    }
                })
    }
}