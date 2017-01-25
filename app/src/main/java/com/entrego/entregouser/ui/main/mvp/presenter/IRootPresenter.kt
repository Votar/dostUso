package com.entrego.entregouser.ui.main.mvp.presenter

import com.entrego.entregouser.ui.main.mvp.view.IRootView

interface IRootPresenter {
    fun onStart(view: IRootView)
    fun onMapReady()
    fun onStop()

}