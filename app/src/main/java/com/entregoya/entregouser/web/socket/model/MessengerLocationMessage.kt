package com.entregoya.entregouser.web.socket.model

import com.google.android.gms.maps.model.LatLng

class MessengerLocationMessage(val order: Long,
                               val subscriber: Long,
                               val id: Long,
                               val coordinates: LatLng,
                               val timestamp: Long,
                               val type: SocketMessageType){
    override fun toString(): String {
        return "MessengerLocationMessage(order=$order, subscriber=$subscriber, id=$id, coordinates=$coordinates, timestamp=$timestamp, type=$type)"
    }
}

