package com.entrego.entregouser.ui.delivery.escort.chat

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.entrego.entregouser.ui.delivery.escort.chat.model.ChatMessageEntity


object ChatContract {
    interface View : IBaseMvpView {

    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun showMessage(message: ChatMessageEntity)
        fun sendMessage(message: String)
    }
}