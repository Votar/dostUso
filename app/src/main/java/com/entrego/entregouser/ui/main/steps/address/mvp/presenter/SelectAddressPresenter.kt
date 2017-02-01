package com.entrego.entregouser.ui.main.steps.address.mvp.presenter

import com.entrego.entregouser.ui.main.steps.address.mvp.view.ISelectAddressView

class SelectAddressPresenter : ISelectAddressPresenter {

    private var mView: ISelectAddressView? = null
    override fun onStart(view: ISelectAddressView) {
        mView = view
        mView?.onCreateAddressList()
    }

    override fun onStop() {
        mView = null
    }
}