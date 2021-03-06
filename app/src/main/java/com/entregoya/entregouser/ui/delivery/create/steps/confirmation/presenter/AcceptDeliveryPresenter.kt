package com.entregoya.entregouser.ui.delivery.create.steps.confirmation.presenter

import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.ui.delivery.create.steps.confirmation.model.RequestDeliveryConfirmation
import com.entregoya.entregouser.ui.delivery.create.steps.confirmation.view.IAcceptDeliveryView
import com.entregoya.entregouser.web.api.ApiContract

class AcceptDeliveryPresenter : IAcceptDeliveryPresenter {

    var mView: IAcceptDeliveryView? = null
    val mResponseListener = object : RequestDeliveryConfirmation.DeliveryConfirmationResponseListener {
        override fun onFailureCreationResponse(code: Int?, message: String?) {
            mView?.hideProgress()
            when (code) {
                ApiContract.RESPONSE_INVALID_TOKEN -> mView?.onLogout()
                else -> mView?.showError(message)
            }
        }

        override fun onSuccessCreationResponse(deliveryView: EntregoDeliveryPreview) {
            mView?.hideProgress()
            mView?.successAccept(deliveryView)
        }
    }

    override fun onStart(view: IAcceptDeliveryView) {
        mView = view
    }

    override fun onStop() {
        mView?.hideProgress()
        mView = null
    }

    override fun acceptDelivery(deliveryId: Long) {
        mView?.showProgress()
        val token = EntregoStorage.getTokenOrEmpty()
        RequestDeliveryConfirmation.requestAsync(deliveryId, token, mResponseListener)
    }
}