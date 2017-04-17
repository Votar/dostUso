package com.entregoya.entregouser.ui.delivery.escort.chat

import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView
import com.entregoya.entregouser.ui.delivery.escort.chat.model.ChatMessageEntity
import com.entregoya.entregouser.web.socket.model.ChatSocketMessage


object ChatContract {
    interface View : IBaseMvpView {
        fun registerChatReceiver()
        fun unregisterChatReceiver()
        fun popupMessage(message: ChatMessageEntity)
        fun popupMessages(listMessages: List<ChatMessageEntity>)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun setupSenderId(userId: Long)
        fun setupOrderId(orderId: Long)
        fun showMessage(message: ChatSocketMessage)
        fun sendMessage(orderId: Long, text: String)
        fun requestChatHistory()
    }
}