package com.entrego.entregouser.mvp.presenter

import com.entrego.entregouser.mvp.view.IBaseMvpView


open class BaseMvpPresenter<V : IBaseMvpView> : IBaseMvpPresenter<V> {

    protected var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }
}