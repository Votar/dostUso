package com.entregoya.entregouser.ui.intro.registration.presenter

import com.entregoya.entregouser.ui.intro.registration.model.RegistrationWithFbRequest
import com.entregoya.entregouser.ui.intro.registration.view.IRegistrationFbView
import com.entregoya.entregouser.web.model.response.common.FieldError

class RegistrationFbPresenter(val view: IRegistrationFbView) : IRegistrationFbPresenter {

    enum class FIELDS(val value: String) {
        EMAIL("email"),
        NAME("name"),
        PASSWORD("password"),
        PHONE_NUMBER("phone.number"),
        PHONE_CODE("phone.code")
    }

    override fun requestRegistration(email: String, name: String, token: String, phoneCode: String, phoneNumber: String) {

        view.showProgress()
        RegistrationWithFbRequest(email, name, token, phoneCode, phoneNumber)
                .requestAsync(object : RegistrationWithFbRequest.ResultListener {
                    override fun onFailureResponse(code: Int?, message: String?) {
                        view.hideProgress()
                        when (code) {
                            3 -> {
                                view.setErrorEmailRegistered()
                                view.setErrorPhoneRegistered()
                            }
                            else -> view.showMessage(message)
                        }
                    }

                    override fun onSuccessResponse() {
                        view.hideProgress()
                        view.successRegistration()
                    }

                    override fun onValidationError(field: FieldError) {
                        view.hideProgress()
                        when (field.field) {

                            FIELDS.EMAIL.value -> view.setErrorEmail(field.message)

                            FIELDS.NAME.value -> view.setErrorName(field.message)

                            FIELDS.PHONE_CODE.value -> view.setErrorPhoneCode(field.message)

                            FIELDS.PHONE_NUMBER.value -> view.setErrorPhoneNumber(field.message)

                            else -> view.showMessage(field.message)
                        }
                    }
                })


    }


}