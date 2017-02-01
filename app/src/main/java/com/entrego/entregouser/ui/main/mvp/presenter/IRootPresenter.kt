package com.entrego.entregouser.ui.main.mvp.presenter

import com.entrego.entregouser.ui.main.mvp.view.IRootView

interface IRootPresenter {
    fun onCreate(view: IRootView)
    fun onStart()
    fun onMapReady()
    fun onStop()


}