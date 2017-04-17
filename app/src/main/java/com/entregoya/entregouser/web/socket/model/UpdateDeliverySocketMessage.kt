package com.entregoya.entregouser.web.socket.model

class UpdateDeliverySocketMessage(val delivery: Long,
                                  type: SocketMessageType) : BaseSocketMessage(type) {

    override fun toString(): String {
        return "UpdateDeliverySocketMessage(delivery=$delivery)"
    }
}
