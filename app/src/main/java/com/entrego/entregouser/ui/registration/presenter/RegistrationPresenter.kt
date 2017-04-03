package com.entrego.entregouser.ui.registration.presenter

import com.entrego.entregouser.R
import com.entrego.entregouser.ui.registration.model.EntregoRegistration
import com.entrego.entregouser.ui.registration.view.IRegistrationView
import com.entrego.entregouser.web.model.response.common.FieldError

class RegistrationPresenter(val view: IRegistrationView) : IRegistrationPresenter {

    enum class FIELDS(val value: String) {
        EMAIL("email"),
        NAME("name"),
        PASSWORD("password"),
        PHONE_NUMBER("phone.number"),
        PHONE_CODE("phone.code")
    }

    override fun requestRegistration(email: String, name: String, password: String, confPassword: String, phoneCode: String, phoneNumber: String) {

        if (password.isEmpty()) {
            view.setErrorPassword(R.string.error_empty_fields)
            return
        }
        if (password != confPassword) {
            view.setErrorConfPassword()
            return
        }


        view.showProgress()
        EntregoRegistration(email, name, password, phoneCode, phoneNumber)
                .requestAsync(object : EntregoRegistration.ResultListener {
                    override fun onFailureResponse(code: Int?, message: String?) {
                        view.hideProgress()
                        when (code) {
                            3 -> view.setErrorEmailRegistered()
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

                            FIELDS.PASSWORD.value -> view.setErrorPassword(field.message)

                            FIELDS.PHONE_CODE.value -> view.setErrorPhoneCode(field.message)

                            FIELDS.PHONE_NUMBER.value -> view.setErrorPhoneNumber(field.message)

                            else -> view.showMessage(field.message)
                        }
                    }
                })


    }


}