package com.entregoya.entregouser.web.model.response.chat

import com.entregoya.entregouser.web.model.response.BaseEntregoResponse
import com.entregoya.entregouser.web.socket.model.ChatSocketMessage


class EntregoGetChatHistoryResponse(code: Int?, message: String?,
                                    val payload: Array<ChatSocketMessage>)
    : BaseEntregoResponse(code, message)