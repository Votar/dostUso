package com.entrego.entregouser.ui.delivery.create.mvp.presenter

import android.app.Activity
import com.entrego.entregouser.ui.delivery.create.mvp.view.IRootView

interface IRootPresenter {
    fun onCreate(view: IRootView)
    fun onStart()
    fun onMapReady()
    fun onStop()
    fun shareLinkOnSite(activity:Activity)
}