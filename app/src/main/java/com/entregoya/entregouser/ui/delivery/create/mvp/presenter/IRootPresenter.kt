package com.entregoya.entregouser.ui.delivery.create.mvp.presenter

import android.app.Activity
import com.entregoya.entregouser.ui.delivery.create.mvp.view.IRootView

interface IRootPresenter {
    fun onCreate(view: IRootView)
    fun onStart()
    fun onMapReady()
    fun onStop()
    fun onDestroy()
    fun shareLinkOnSite(activity:Activity)
    fun showConditions()
    fun showAboutEntrego()
    fun showContactUs()
    fun showFaq()
    fun showWorkForUs()
    fun showUserManual()
    fun isViewAvailable() :Boolean
}