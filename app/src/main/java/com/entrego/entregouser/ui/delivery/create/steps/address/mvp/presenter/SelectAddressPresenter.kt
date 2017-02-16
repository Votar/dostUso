package com.entrego.entregouser.ui.delivery.create.steps.address.mvp.presenter

import com.entrego.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entrego.entregouser.ui.delivery.create.steps.address.mvp.model.RequestDeliveryCreation
import com.entrego.entregouser.ui.delivery.create.steps.address.mvp.view.ISelectAddressView
import com.entrego.entregouser.web.model.response.delivery.create.DeliveryCreationResponse

class SelectAddressPresenter : ISelectAddressPresenter {

    private var mView: ISelectAddressView? = null
    override fun onStart(view: ISelectAddressView) {
        mView = view
    }

    override fun onStop() {
        mView = null
    }

    val createDeliveryListener = object : RequestDeliveryCreation.DeliveryCreationResponseListener {
        override fun onFailureCreationResponse(code: Int?, message: String?) {
            mView?.hideProgress()
        }

        override fun onSuccessCreationResponse(response: DeliveryCreationResponse) {
            mView?.hideProgress()
            mView?.showAcceptView(response)
        }
    }

    override fun requestDeliveryCreation(builder: DeliveryEntityBuilder) {
        mView?.showProgress()
        RequestDeliveryCreation.requestAsync(createDeliveryListener)
    }

}