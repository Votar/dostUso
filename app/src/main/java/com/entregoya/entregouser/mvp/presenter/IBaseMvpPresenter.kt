package com.entregoya.entregouser.mvp.presenter

import com.entregoya.entregouser.mvp.view.IBaseMvpView


interface IBaseMvpPresenter<in V : IBaseMvpView> {

    fun attachView(view: V)
    fun detachView()
}