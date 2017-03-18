package com.entrego.entregouser.web.socket.model

enum class SocketMessageType {
    ORDER,
    ORDER_STATUS,
    WAYPOINT,
    TRACK,
    TRACK_LIST,
    MESSAGE,
    TRACK_ORDER
}