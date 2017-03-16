package com.entrego.entregouser.web.model.response.chat

import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import com.entrego.entregouser.web.socket.model.ChatSocketMessage


class EntregoGetChatHistoryResponse(code: Int?, message: String?,
                                    val payload: Array<ChatSocketMessage>)
    : BaseEntregoResponse(code, message)