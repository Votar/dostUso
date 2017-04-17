package com.entregoya.entregouser.entity.back

import com.entregoya.entregouser.entity.common.EntregoMessengerView

class EntregoOrderView(
        val id:Long,
        val waypoints: Array<EntregoWaypoint>,
        val messenger: EntregoMessengerView) {

    override fun toString(): String {
        return "EntregoOrderView(id=$id, messenger=$messenger)"
    }
}