package com.entrego.entregouser.ui.escort.root

import android.app.FragmentManager
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.google.android.gms.maps.model.LatLng


object EscortContract {
    interface View : IBaseMvpView {
        fun getSupportFragmentManager(): android.support.v4.app.FragmentManager
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun cancelDelivery()
        fun showStatusDelivery()
        fun shareRouteBySocial()
        fun loadMapAsync()
        fun moveCamera(position: LatLng)
    }
}