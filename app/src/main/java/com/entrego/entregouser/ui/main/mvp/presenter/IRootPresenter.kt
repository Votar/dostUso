package com.entrego.entregouser.ui.main.mvp.presenter

import android.view.View
import com.entrego.entregouser.ui.main.mvp.view.IRootView

interface IRootPresenter {
    fun onStart(view: IRootView)
    fun onMapReady()
    fun onStop()
    fun getTabClickListener(): (View) -> Unit


}