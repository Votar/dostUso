package com.entrego.entregouser.ui.create.steps.accept.presenter

import com.entrego.entregouser.ui.create.steps.accept.model.RequestDeliveryConfirmation
import com.entrego.entregouser.ui.create.steps.accept.view.IAcceptDeliveryView

class AcceptDeliveryPresenter : IAcceptDeliveryPresenter {

    var mView: IAcceptDeliveryView? = null
    val mResponseListener = object : RequestDeliveryConfirmation.DeliveryConfirmationResponseListener {
        override fun onFailureCreationResponse(code: Int?, message: String?) {
            mView?.hideProgress()
        }

        override fun onSuccessCreationResponse() {
            mView?.hideProgress()
            mView?.successAccept()
        }
    }

    override fun onStart(view: IAcceptDeliveryView) {
        mView = view
    }

    override fun onStop() {
        mView?.hideProgress()
        mView = null
    }

    override fun acceptDelivery(deliveryId: Long?) {
        mView?.showProgress()
        RequestDeliveryConfirmation.requestAsync(mResponseListener)
    }
}