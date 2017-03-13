package com.entrego.entregouser.web.socket.model

class UpdateDeliverySocketMessage(val delivery: Long, override val type: SocketMessageType)
    : BaseSocketMessage(type) {

    override fun toString(): String {
        return "UpdateDeliverySocketMessage(delivery=$delivery, type=$type)"
    }
}
