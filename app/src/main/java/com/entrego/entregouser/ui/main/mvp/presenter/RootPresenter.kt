package com.entrego.entregouser.ui.main.mvp.presenter

import com.entrego.entregouser.ui.main.mvp.view.IRootView

class RootPresenter : IRootPresenter {

    var mView: IRootView? = null
    override fun onStart(view: IRootView) {
        mView = view
    }

    override fun onMapReady() {

    }

    override fun onStop() {
        mView = null
    }
}
