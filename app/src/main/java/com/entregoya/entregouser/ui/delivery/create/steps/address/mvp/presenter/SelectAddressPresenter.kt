package com.entregoya.entregouser.ui.delivery.create.steps.address.mvp.presenter

import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.ui.delivery.create.steps.address.mvp.model.RequestDeliveryCreation
import com.entregoya.entregouser.ui.delivery.create.steps.address.mvp.view.ISelectAddressView

class SelectAddressPresenter : ISelectAddressPresenter {

    private var mView: ISelectAddressView? = null
    override fun onStart(view: ISelectAddressView) {
        mView = view
    }

    override fun onStop() {
        mView = null
    }


    override fun requestDeliveryCreation(builder: DeliveryEntityBuilder) {

        if (validAddressesCounts(builder.addresses).not()) {
            mView?.onShowMessage(R.string.error_invalid_addresses_count)
            return
        } else if (validEmptyAddresses(builder.addresses).not())
            return

        mView?.showProgress()
        builder.payment = EntregoStorage.getDefaultPaymentMethod().toPaymentBody()
        RequestDeliveryCreation.requestAsync(builder, createDeliveryListener)

    }

    val createDeliveryListener = object : RequestDeliveryCreation.DeliveryCreationResponseListener {
        override fun onFailureCreationResponse(code: Int?, message: String?) {
            mView?.hideProgress()
            when (code) {
                2 -> mView?.onLogout()
            }

        }

        override fun onSuccessCreationResponse(response: EntregoDeliveryPreview) {
            mView?.hideProgress()
            mView?.showAcceptView(response)
        }
    }

    fun validAddressesCounts(addressList: List<String>?): Boolean {
        if (addressList == null)
            return false
        else
            return addressList.count() >= 2
    }

    fun validEmptyAddresses(addressList: List<String>?): Boolean {
        addressList?.forEachIndexed { index, value ->
            run {
                if (value.isNullOrEmpty()) {
                    mView?.showErrorAddress(R.string.error_invalid_address_with_index, index)
                    return false
                }
            }
        }
        return true
    }
}