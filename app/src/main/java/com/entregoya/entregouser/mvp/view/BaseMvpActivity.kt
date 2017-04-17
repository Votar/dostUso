package com.entregoya.entregouser.mvp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.storage.preferences.PreferencesManager
import com.entregoya.entregouser.ui.auth.AuthActivity
import com.entregoya.entregouser.util.logout
import com.entregoya.entregouser.util.showSnack
import com.entregoya.entregouser.util.showSnackError


abstract class BaseMvpActivity<in V : IBaseMvpView, T : IBaseMvpPresenter<V>>
    : AppCompatActivity(), IBaseMvpView {
    protected abstract var mPresenter: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun getAppContext(): Context = this

    override fun showError(error: String?) {
        getRootView()?.showSnackError(error)
    }

    override fun showError(stringResId: Int) {
        getRootView()?.showSnackError(getString(stringResId))
    }

    override fun showMessage(srtResId: Int) {
        getRootView()?.showSnack(getString(srtResId))
    }

    override fun showMessage(message: String) {
        getRootView()?.showSnack(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onLogout() {
        this.logout()
    }
}