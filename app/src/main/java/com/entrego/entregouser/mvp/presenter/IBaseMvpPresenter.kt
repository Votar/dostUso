package com.entrego.entregouser.mvp.presenter

import com.entrego.entregouser.mvp.view.IBaseMvpView


interface IBaseMvpPresenter<in V : IBaseMvpView> {

    fun attachView(view: V)
    fun detachView()
}