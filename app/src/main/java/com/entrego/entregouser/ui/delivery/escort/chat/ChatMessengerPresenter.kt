package com.entrego.entregouser.ui.delivery.escort.chat

import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.ui.delivery.escort.chat.model.ChatMessageEntity

class ChatMessengerPresenter : BaseMvpPresenter<ChatContract.View>(),
ChatContract.Presenter{
    override fun showMessage(message: ChatMessageEntity) {

    }

    override fun sendMessage(message: String) {

    }

}